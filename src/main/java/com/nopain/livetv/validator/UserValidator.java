package com.nopain.livetv.validator;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.exception.signup.SignUpException;
import com.nopain.livetv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidator {
    private static final String EMAIL_ALREADY_EXISTS = "signup.email_already_exists";
    private static final String USERNAME_ALREADY_EXISTS = "signup.username_already_exists";
    private final UserRepository userRepository;
    private final MessageSource messageSource;

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

            throw new SignUpException(messageSource.getMessage(USERNAME_ALREADY_EXISTS, null, Locale.getDefault()));
        }

    }

    private void checkEmail(String email) {

        final boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail) {

            log.warn("{} is already being used!", email);

            throw new SignUpException(messageSource.getMessage(EMAIL_ALREADY_EXISTS, null, Locale.getDefault()));
        }
    }
}
