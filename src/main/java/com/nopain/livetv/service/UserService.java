package com.nopain.livetv.service;

import com.nopain.livetv.dto.AuthenticatedUser;
import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.model.User;

public interface UserService {
    public User findByUsername(String username);
    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public AuthenticatedUser findAuthenticatedUserByUsername(String username);
}
