package com.nopain.livetv.dto.stomp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViewsCountUpdatedMessage {
    private Long livestreamId;
    private int viewsCount;
}
