package com.nopain.livetv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse extends ApplicationModelResponse {
    private String content;
    private Long livestreamId;
    private Long triggeredUserId;
}
