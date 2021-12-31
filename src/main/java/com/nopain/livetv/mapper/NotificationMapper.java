package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.NotificationResponse;
import com.nopain.livetv.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    List<NotificationResponse> toResponseList(List<Notification> notifications);

    NotificationResponse toResponse(Notification notification);
}
