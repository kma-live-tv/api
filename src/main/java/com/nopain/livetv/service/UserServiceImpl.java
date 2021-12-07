package com.nopain.livetv.service;

import com.nopain.livetv.dto.AuthenticatedUser;
import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.mapper.UserMapper;
import com.nopain.livetv.model.User;
import com.nopain.livetv.repository.UserRepository;
import com.nopain.livetv.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String SIGN_UP_SUCCESSFUL = "sign_up_successful";
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository
                .findUserByUsername(username)
                .orElseThrow();
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {

        userValidator.validateUser(signUpRequest);
        log.info("signupRequest");
        log.info(signUpRequest.toString());

        final User user = UserMapper.INSTANCE.convertToUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        log.info(user.toString());

        userRepository.save(user);

        final String username = signUpRequest.getUsername();

        log.info("{} registered successfully!", username);

        return new SignUpResponse(SIGN_UP_SUCCESSFUL);
    }

    public AuthenticatedUser findAuthenticatedUserByUsername(String username) {

        final User user = findByUsername(username);

        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
    }
}
