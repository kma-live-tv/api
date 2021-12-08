package com.nopain.livetv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty
    @Schema(example = "user1")
    private String username;

    @NotEmpty
    @Schema(example = "Password1!")
    private String password;
}
