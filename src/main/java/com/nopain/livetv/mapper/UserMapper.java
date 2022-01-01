package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.UserResponse;
import com.nopain.livetv.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(SignUpRequest signUpRequest);

    UserResponse toResponse(User user);

    User convertToUser(UserResponse userResponse);

    List<UserResponse> toResponseList(List<User> users);
}
