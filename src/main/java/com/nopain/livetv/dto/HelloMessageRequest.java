package com.nopain.livetv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelloMessageRequest {
    @Schema(example = "Tran Cong")
    private String name;
}
