package com.nopain.livetv.controller;

import com.nopain.livetv.decorator.JWTSecured;
import com.nopain.livetv.dto.NotificationResponse;
import com.nopain.livetv.mapper.NotificationMapper;
import com.nopain.livetv.security.model.UserDetailsImpl;
import com.nopain.livetv.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationsController {
    private final NotificationService service;

    @JWTSecured
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<NotificationResponse>> owned(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var user = userDetails.getUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        NotificationMapper.INSTANCE.toResponseList(
                                service.owned(user)
                        )
                );
    }

    @JWTSecured
    @PutMapping("/{id}/read")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NotificationResponse> markAsRead(
            @PathVariable Long id
    ) {
        var updated = service.markAsRead(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NotificationMapper.INSTANCE.toResponse(updated));
    }
}
