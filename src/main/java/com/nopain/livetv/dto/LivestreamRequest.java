package com.nopain.livetv.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class LivestreamRequest {
    @Size(min = 4, max = 100)
    private String content;
}
