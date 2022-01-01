package com.nopain.livetv.service;

import com.nopain.livetv.dto.SignUpRequest;
import com.nopain.livetv.dto.SignUpResponse;
import com.nopain.livetv.dto.UserResponse;
import com.nopain.livetv.mapper.UserMapper;
import com.nopain.livetv.model.User;
import com.nopain.livetv.repository.UserRepository;
import com.nopain.livetv.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private static final String SIGN_UP_SUCCESSFUL = "sign_up_successful";
    private final UserRepository repository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return repository
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

        repository.save(user);

        final String username = signUpRequest.getUsername();

        log.info("{} registered successfully!", username);

        return new SignUpResponse(SIGN_UP_SUCCESSFUL);
    }

    public UserResponse findAuthenticatedUserByUsername(String username) {

        final User user = findByUsername(username);

        return UserMapper.INSTANCE.toResponse(user);
    }

    public List<User> allUsers(User currentUser) {
        return setManyIsFollowed(repository.findAll(), currentUser);
    }

    public User findUser(Long id, User currentUser) {
        return setIsFollowed(repository.findById(id).orElseThrow(), currentUser);
    }

    public List<User> setManyIsFollowed(List<User> users, User currentUser) {
        users.forEach(user -> {
            setIsFollowed(user, currentUser);
        });

        return users;
    }

    public User setIsFollowed(User user, User currentUser) {
        var followers = user.getFollowers();
        var currentFollower = followers
                .stream()
                .filter(
                        follower -> follower.getFollowedBy().getId().equals(currentUser.getId())
                ).findFirst();

        user.setFollowed(currentFollower.isPresent());

        return user;
    }
}
