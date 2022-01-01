package com.nopain.livetv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse extends ApplicationModelResponse {
    private String username;
    private String email;
    private String displayName;
    private Long balance;
    private boolean isFollowed;
}
