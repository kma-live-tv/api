package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.*;
import com.nopain.livetv.exception.common.HttpException;
import com.nopain.livetv.mapper.CommentMapper;
import com.nopain.livetv.mapper.LivestreamMapper;
import com.nopain.livetv.mapper.ReactionMapper;
import com.nopain.livetv.security.model.UserDetailsImpl;
import com.nopain.livetv.service.LivestreamService;
import com.nopain.livetv.service.StompService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/livestreams")
@RequiredArgsConstructor
public class LivestreamsController {
    private final LivestreamService service;
    private final StompService stompService;

    @JWTSecured
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LivestreamResponse> create(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody LivestreamRequest request
    ) throws HttpException {
        var livestream = service.create(userDetails.getUser(), request);

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
                .body(LivestreamMapper.INSTANCE.toResponseList(ownedLivestreams));
    }

    @JWTSecured
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LivestreamResponse> get(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LivestreamMapper.INSTANCE.toResponse(
                        service.find(id)
                ));
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
        var comment = service.comment(user, id, commentRequest);
        var response = CommentMapper.INSTANCE.toResponse(comment);
        stompService.pubNewComment(id, response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @JWTSecured
    @PostMapping("/{id}/reaction")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReactionResponse> react(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ReactionRequest reactionRequest,
            @PathVariable Long id
    ) {
        var user = userDetails.getUser();
        var reaction = service.react(user, id, reactionRequest);
        var response = ReactionMapper.INSTANCE.toResponse(reaction);
        stompService.pubNewReaction(id, response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
