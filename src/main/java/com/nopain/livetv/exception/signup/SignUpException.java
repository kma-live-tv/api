package com.nopain.livetv.exception.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpException extends RuntimeException {
    private final String message;
}
