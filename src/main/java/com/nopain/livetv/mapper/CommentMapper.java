package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.CommentResponse;
import com.nopain.livetv.dto.LivestreamResponse;
import com.nopain.livetv.model.Comment;
import com.nopain.livetv.model.Livestream;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentResponse toResponse(Comment comment);
}
