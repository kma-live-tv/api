package com.nopain.livetv.service;

import com.nopain.livetv.exception.common.HttpException;
import com.nopain.livetv.model.Follower;
import com.nopain.livetv.model.User;
import com.nopain.livetv.repository.FollowerRepository;
import com.nopain.livetv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowerRepository repository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void follow(User currentUser, Long toUserId) throws HttpException {
        var follower = repository.findByFollowedByIdAndFollowToId(currentUser.getId(), toUserId);

        if (follower.isPresent()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Bạn đã theo dõi người dùng này rồi");
        }

        var toUser = userRepository.findById(toUserId).orElseThrow();

        var createdFollower = Follower
                .builder()
                .followedBy(currentUser)
                .followTo(toUser)
                .build();
        repository.save(createdFollower);

        notificationService.pushFollowEvent(createdFollower);
    }

    public void unfollow(User currentUser, Long toUserId) {
        var follower = repository.findByFollowedByIdAndFollowToId(currentUser.getId(), toUserId);

        follower.ifPresent(repository::delete);
    }
}
