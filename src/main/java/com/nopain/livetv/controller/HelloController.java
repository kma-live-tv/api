package com.nopain.livetv.controller;

import com.nopain.livetv.dto.HelloMessageRequest;
import com.nopain.livetv.dto.stomp.GreetingMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class HelloController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/hello")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GreetingMessage> sayHello(@Valid @RequestBody HelloMessageRequest message) {
        var content = "Hello " + message.getName();
        simpMessagingTemplate.convertAndSend("/topic/greetings", new GreetingMessage(content));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GreetingMessage(content));
    }
}
