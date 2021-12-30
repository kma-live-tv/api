package com.nopain.livetv.dto.callback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DvrEvent extends BaseStreamEvent {
    private String file;
}
