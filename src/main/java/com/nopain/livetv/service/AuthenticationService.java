package com.nopain.livetv.service;

import com.nopain.livetv.dto.LoginRequest;
import com.nopain.livetv.dto.LoginResponse;
import com.nopain.livetv.mapper.UserMapper;
import com.nopain.livetv.security.jwt.JwtTokenProvider;
import com.nopain.livetv.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        final var username = loginRequest.getUsername();
        final var password = loginRequest.getPassword();

        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        final var authenticatedUser = userService.findAuthenticatedUserByUsername(username);
        final var user = UserMapper.INSTANCE.convertToUser(authenticatedUser);
        final var token = jwtTokenProvider.createAccessToken(new UserDetailsImpl(user));

        log.info("{} has successfully logged in!", user.getUsername());

        return new LoginResponse(token);
    }
}
