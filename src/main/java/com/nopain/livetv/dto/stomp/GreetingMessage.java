package com.nopain.livetv.dto.stomp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GreetingMessage {
    @Schema(example = "Hello Cong")
    private String content;
}
