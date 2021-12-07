package com.nopain.livetv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpRequest {
    @NotEmpty(message = "{sign_up_display_name_not_empty}")
    @Schema(example = "Trần Công")
    private String displayName;

    @Email(message = "{sign_up_email_is_not_valid}")
    @NotEmpty(message = "{sign_up_email_not_empty}")
    @Schema(example = "example@gmail.com")
    private String email;

    @NotEmpty(message = "{sign_up_username_not_empty}")
    @Schema(example = "user1")
    private String username;

    @NotEmpty(message = "{sign_up_password_not_empty}")
    @Schema(example = "Password1!")
    private String password;
}
