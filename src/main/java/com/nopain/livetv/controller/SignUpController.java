package com.nopain.livetv.controller;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class SignUpController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        final SignUpResponse signUpResponse = userService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }
}
