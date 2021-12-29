package com.nopain.livetv.dto;

import com.nopain.livetv.model.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionResponse extends ApplicationModelResponse {
    private ReactionType type;
    private UserResponse user;
}
