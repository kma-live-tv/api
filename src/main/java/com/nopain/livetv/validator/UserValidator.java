package com.nopain.livetv.validator;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.exception.signup.SignUpException;
import com.nopain.livetv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidator {
    private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
    private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";
    private final UserRepository userRepository;

    public void validateUser(SignUpRequest signUpRequest) {

        final String email = signUpRequest.getEmail();
        final String username = signUpRequest.getUsername();

        checkEmail(email);
        checkUsername(username);
    }

    private void checkUsername(String username) {

        final boolean existsByUsername = userRepository.existsByUsername(username);

        if (existsByUsername) {

            log.warn("{} is already being used!", username);

            throw new SignUpException(USERNAME_ALREADY_EXISTS);
        }

    }

    private void checkEmail(String email) {

        final boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail) {

            log.warn("{} is already being used!", email);

            throw new SignUpException(EMAIL_ALREADY_EXISTS);
        }
    }
}
