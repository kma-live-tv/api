package com.nopain.livetv.controller;

import com.nopain.livetv.dto.callback.BaseStreamEvent;
import com.nopain.livetv.dto.callback.DvrEvent;
import com.nopain.livetv.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/callback")
@RequiredArgsConstructor
public class CallbackController {
    private final CallbackService service;

    @PostMapping("/streams")
    public ResponseEntity<Integer> onStreams(@Valid @RequestBody BaseStreamEvent event) throws NotFoundException {
        log.info("STREAM EVENT");
        service.onEvent(event);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(0);
    }

    @PostMapping("/dvrs")
    public ResponseEntity<Integer> onDvrs(@Valid @RequestBody DvrEvent event) throws NotFoundException {
        service.onEvent(event);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(0);
    }
}
