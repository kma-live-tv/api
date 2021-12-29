package com.nopain.livetv.mapper;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.UserResponse;
import com.nopain.livetv.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(SignUpRequest signUpRequest);

    UserResponse convertToAuthenticatedUserDto(User user);

    User convertToUser(UserResponse userResponse);
}
