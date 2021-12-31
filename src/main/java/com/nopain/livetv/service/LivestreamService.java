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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivestreamService {
    private final LivestreamRepository repository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    public Livestream find(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Livestream findByStreamKey(String streamKey) {
        return repository.findByStreamKey(streamKey).orElseThrow();
    }

    public Livestream create(User user, LivestreamRequest request) throws HttpException {
        var streamingCount = repository.countByStatus(LivestreamStatus.STREAMING);

        if (streamingCount > 0) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Bạn đang livestream rồi");
        }

        var livestream = Livestream
                .builder()
                .user(user)
                .streamKey(UUID.randomUUID().toString())
                .content(request.getContent())
                .build();
        repository.save(livestream);

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
}