package com.nopain.livetv.dto;

import com.nopain.livetv.model.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionRequest {
    @NotEmpty
    private ReactionType type;
}
