package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.UserResponse;
import com.nopain.livetv.exception.common.HttpException;
import com.nopain.livetv.mapper.UserMapper;
import com.nopain.livetv.security.model.UserDetailsImpl;
import com.nopain.livetv.service.FollowService;
import com.nopain.livetv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final FollowService followService;
    private final UserService userService;

    @GetMapping("/me")
    @JWTSecured
    public ResponseEntity<UserResponse> getAuthenticatedUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        var user = userDetails.getUser();

        return ResponseEntity
                .status(200)
                .body(UserMapper.INSTANCE.toResponse(user));
    }

    @PostMapping("/{id}/follow")
    @JWTSecured
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> follow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) throws HttpException {
        followService.follow(userDetails.getUser(), id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @PostMapping("/{id}/unfollow")
    @JWTSecured
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> unfollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        followService.unfollow(userDetails.getUser(), id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @GetMapping
    @JWTSecured
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponse>> all(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.INSTANCE.toResponseList(userService.allUsers(
                        userDetails.getUser()
                )));
    }

    @GetMapping("/{id}")
    @JWTSecured
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> get(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.INSTANCE.toResponse(userService.findUser(id, userDetails.getUser())));
    }
}
