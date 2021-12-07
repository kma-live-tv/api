package com.nopain.livetv.exception.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpException extends RuntimeException {
    private String message;
}
