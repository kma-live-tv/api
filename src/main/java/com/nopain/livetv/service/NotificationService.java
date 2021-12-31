package com.nopain.livetv.service;

import com.nopain.livetv.model.Follower;
import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.Notification;
import com.nopain.livetv.model.User;
import com.nopain.livetv.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;
    private final StompService stompService;

    public List<Notification> owned(User user) {
        return user.getNotifications();
    }

    public void pushFollowEvent(Follower follower) {
        var followTo = follower.getFollowTo();
        var from = follower.getFollowedBy();

        var notification = Notification
                .builder()
                .content(
                        String.format("%s đã bắt đầu theo dõi bạn", from.getDisplayName())
                )
                .user(followTo)
                .triggeredUserId(from.getId())
                .build();
        repository.save(notification);

        stompService.pubNewNotification(notification);
    }

    public void pushPublishEvent(Livestream livestream) {
        var user = livestream.getUser();
        var followers = user.getFollowers();

        followers.forEach(follower -> {
            var followerUser = follower.getFollowedBy();

            var notification = Notification
                    .builder()
                    .content(
                            String.format(
                                    "%s đang phát trực tiếp: \"%s\"",
                                    user.getDisplayName(),
                                    truncate(livestream.getContent())
                            )
                    )
                    .user(followerUser)
                    .livestreamId(livestream.getId())
                    .build();
            repository.save(notification);

            stompService.pubNewNotification(notification);
        });
    }

    private String truncate(String source) {
        var truncated = source.length() > 10;

        return truncated ? String.format("%s...", source.substring(0, 10)) : source;
    }
}
