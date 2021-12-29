package com.nopain.livetv.service;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.dto.UserResponse;
import com.nopain.livetv.model.User;

public interface UserService {
    User findByUsername(String username);

    SignUpResponse signUp(SignUpRequest signUpRequest);

    UserResponse findAuthenticatedUserByUsername(String username);
}
