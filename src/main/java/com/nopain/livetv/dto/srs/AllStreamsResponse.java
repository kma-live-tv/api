package com.nopain.livetv.dto.srs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllStreamsResponse {
    private int code;
    private String server;
    private List<StreamResponse> streams;
}
