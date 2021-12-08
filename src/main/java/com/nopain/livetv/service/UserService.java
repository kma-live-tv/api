package com.nopain.livetv.service;

import com.nopain.livetv.dto.AuthenticatedUser;
import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.model.User;

public interface UserService {
    User findByUsername(String username);

    SignUpResponse signUp(SignUpRequest signUpRequest);

    AuthenticatedUser findAuthenticatedUserByUsername(String username);
}
