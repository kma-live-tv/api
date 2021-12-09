package com.nopain.livetv.exception.handler;

import com.nopain.livetv.exception.signup.SignUpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex) {
        log.info("Unknown exception: {} ", ex.toString());
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                messageSource.getMessage("exception.internal_server_error",
                        null,
                        Locale.getDefault()),
                ex.toString());
    }

    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSignUpException(SignUpException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.toString());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(),
                messageSource.getMessage("login.bad_credentials",
                        null,
                        Locale.getDefault()),
                ex.toString());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(),
                messageSource.getMessage("exception.access_denied",
                        null,
                        Locale.getDefault()),
                ex.toString());
    }
}
