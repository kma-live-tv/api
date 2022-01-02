package com.nopain.livetv.dto.callback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseStreamEvent {
    private ActionType action;
    private Long stream;
}
