package com.nopain.livetv.dto.stomp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DvrDoneMessage {
    private Long livestreamId;
    private String dvrFile;
}
