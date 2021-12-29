package com.nopain.livetv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivestreamResponse extends ApplicationModelResponse {
    private String streamKey;
    private String dvrFile;

    private List<CommentResponse> comments;
    private List<ReactionResponse> reactions;
    private UserResponse user;
}
