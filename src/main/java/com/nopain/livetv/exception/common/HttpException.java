package com.nopain.livetv.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpException extends Exception {
    private final HttpStatus status;
    private final String message;
}
