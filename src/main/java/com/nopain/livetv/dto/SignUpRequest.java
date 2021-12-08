package com.nopain.livetv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpRequest {
    @NotEmpty
    @Size(min = 4, max = 32)
    @Schema(example = "Trần Công")
    private String displayName;

    @Email
    @NotEmpty
    @Schema(example = "example@gmail.com")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 32)
    @Schema(example = "user1")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 32)
    @Schema(example = "Password1!")
    private String password;
}
