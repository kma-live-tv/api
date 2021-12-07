package com.nopain.livetv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUser {
    private String username;
    private String email;
    private String displayName;
    private Long balance;
}
