package com.nopain.livetv.controller;

import com.nopain.livetv.model.GreetingMessage;
import com.nopain.livetv.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @MessageMapping("/hello")
    @SendTo("/hello/greetings")
    public GreetingMessage hello(HelloMessage message) {
        return new GreetingMessage("Hello " + message.getName());
    }
}
