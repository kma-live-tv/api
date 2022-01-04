package com.nopain.livetv.service;

import com.nopain.livetv.dto.CommentRequest;
import com.nopain.livetv.dto.LivestreamRequest;
import com.nopain.livetv.dto.ReactionRequest;
import com.nopain.livetv.exception.common.HttpException;
import com.nopain.livetv.model.*;
import com.nopain.livetv.repository.CommentRepository;
import com.nopain.livetv.repository.LivestreamRepository;
import com.nopain.livetv.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivestreamService {
    private final LivestreamRepository repository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;
    private final NotificationService notificationService;
    private final StompService stompService;

    public List<Livestream> streamings() {
        return repository.findByStatusIn(new LivestreamStatus[]{
                LivestreamStatus.STREAMING,
                LivestreamStatus.WAITING
        });
    }

    public Livestream find(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Livestream create(User user, LivestreamRequest request) throws HttpException {
        var streaming = repository.findByUserIdAndStatus(user.getId(), LivestreamStatus.STREAMING);

        if (streaming.isPresent()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Bạn đang livestream rồi");
        }

        var waiting = repository.findByUserIdAndStatus(user.getId(), LivestreamStatus.WAITING);

        if (waiting.isPresent()) {
            return waiting.get();
        }

        var livestream = Livestream
                .builder()
                .user(user)
                .content(request.getContent())
                .status(LivestreamStatus.WAITING)
                .build();
        repository.save(livestream);
        notificationService.pushStartEvent(livestream);

        return livestream;
    }

    public Comment comment(User user, Long livestreamId, CommentRequest request) {
        var comment = Comment
                .builder()
                .user(user)
                .content(request.getContent())
                .livestream(find(livestreamId))
                .build();
        commentRepository.save(comment);

        return comment;
    }

    public Reaction react(User user, Long livestreamId, ReactionRequest request) {
        var reaction = Reaction
                .builder()
                .user(user)
                .type(request.getType())
                .livestream(find(livestreamId))
                .build();
        reactionRepository.save(reaction);

        return reaction;
    }

    public List<Livestream> ofUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public void endLivestream(Livestream livestream) {
        livestream.setStatus(LivestreamStatus.END);
        repository.save(livestream);
        stompService.pubEndLivestream(livestream.getId());
    }

    @Scheduled(fixedRate = 10000)
    @Async
    public void closeTimeoutLivestreams() {
        var waitings = repository.findTimeoutLivestreams(
                Instant.now().minus(30, ChronoUnit.SECONDS)
        );

        waitings.forEach(this::endLivestream);
    }
}
