package com.nopain.livetv.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse extends ApplicationModelResponse {
    private String content;
    private UserResponse user;
}
