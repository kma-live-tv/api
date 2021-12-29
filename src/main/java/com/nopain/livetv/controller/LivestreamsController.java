package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.*;
import com.nopain.livetv.mapper.CommentMapper;
import com.nopain.livetv.mapper.LivestreamMapper;
import com.nopain.livetv.mapper.ReactionMapper;
import com.nopain.livetv.model.Comment;
import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.Reaction;
import com.nopain.livetv.repository.CommentRepository;
import com.nopain.livetv.repository.LivestreamRepository;
import com.nopain.livetv.repository.ReactionRepository;
import com.nopain.livetv.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livestreams")
@RequiredArgsConstructor
public class LivestreamsController {
    private final LivestreamRepository repository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    @JWTSecured
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LivestreamResponse> create(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        var user = userDetails.getUser();
        var livestream = Livestream
                .builder()
                .user(user)
                .streamKey(UUID.randomUUID().toString())
                .build();
        repository.save(livestream);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(LivestreamMapper.INSTANCE.toResponse(livestream));
    }

    @JWTSecured
    @GetMapping("/owned")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LivestreamResponse>> ownedList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        var user = userDetails.getUser();
        var ownedLivestreams = user.getLivestreams();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LivestreamMapper.INSTANCE.toListResponse(ownedLivestreams));
    }

    @JWTSecured
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LivestreamResponse> get(
            @PathVariable Long id
    ) {
        var livestream = repository.findById(id).orElseThrow();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LivestreamMapper.INSTANCE.toResponse(livestream));
    }

    @JWTSecured
    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> comment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CommentRequest commentRequest,
            @PathVariable Long id
    ) {
        var user = userDetails.getUser();
        var comment = Comment
                .builder()
                .user(user)
                .content(commentRequest.getContent())
                .livestream(repository.findById(id).orElseThrow())
                .build();
        commentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommentMapper.INSTANCE.toResponse(comment));
    }

    @JWTSecured
    @PostMapping("/{id}/reaction")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReactionResponse> create(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ReactionRequest reactionRequest,
            @PathVariable Long id
    ) {
        var user = userDetails.getUser();
        var reaction = Reaction
                .builder()
                .user(user)
                .type(reactionRequest.getType())
                .livestream(repository.findById(id).orElseThrow())
                .build();
        reactionRepository.save(reaction);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReactionMapper.INSTANCE.toResponse(reaction));
    }
}
