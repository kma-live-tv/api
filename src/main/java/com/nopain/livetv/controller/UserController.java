package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.AuthenticatedUser;
import com.nopain.livetv.mapper.UserMapper;
import com.nopain.livetv.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    @JWTSecured
    public ResponseEntity<AuthenticatedUser> getAuthenticatedUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        var user = userDetails.getUser();

        return ResponseEntity
                .status(200)
                .body(UserMapper.INSTANCE.convertToAuthenticatedUserDto(user));
    }
}
