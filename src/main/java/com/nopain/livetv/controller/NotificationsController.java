package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.NotificationResponse;
import com.nopain.livetv.mapper.NotificationMapper;
import com.nopain.livetv.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationsController {
    @JWTSecured
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<NotificationResponse>> owned(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var user = userDetails.getUser();
        var notifications = user.getNotifications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        NotificationMapper.INSTANCE.toResponseList(notifications)
                );
    }
}
