package com.nopain.livetv.controller;

import com.nopain.livetv.dto.LoginRequest;
import com.nopain.livetv.dto.LoginResponse;
import com.nopain.livetv.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.login(loginRequest));
    }
}
