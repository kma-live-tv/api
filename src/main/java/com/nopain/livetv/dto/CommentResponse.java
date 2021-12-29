package com.nopain.livetv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse extends ApplicationModelResponse {
    private String content;
    private UserResponse user;
}
