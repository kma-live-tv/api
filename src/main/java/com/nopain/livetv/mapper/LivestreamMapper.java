package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.LivestreamResponse;
import com.nopain.livetv.model.Livestream;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LivestreamMapper {
    LivestreamMapper INSTANCE = Mappers.getMapper(LivestreamMapper.class);

    LivestreamResponse toResponse(Livestream livestream);

    List<LivestreamResponse> toResponseList(List<Livestream> livestreams);
}
