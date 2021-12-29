package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.CommentResponse;
import com.nopain.livetv.dto.ReactionResponse;
import com.nopain.livetv.model.Comment;
import com.nopain.livetv.model.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReactionMapper {
    ReactionMapper INSTANCE = Mappers.getMapper(ReactionMapper.class);

    ReactionResponse toResponse(Reaction reaction);
}
