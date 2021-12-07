package com.nopain.livetv.exception.handler;

import com.nopain.livetv.exception.signup.SignUpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex) {
        log.info("Unknown exception: {} ", Exception.class);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSignUpException(SignUpException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "bad_credentials");
    }
}
