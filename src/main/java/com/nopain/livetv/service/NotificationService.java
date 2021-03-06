package com.nopain.livetv.service;

import com.nopain.livetv.model.*;
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
        return repository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public void pushDonateReceived(User user, Order order) {
        var donator = order.getUser();
        var notification = Notification
                .builder()
                .content(
                        String.format("%s đã tặng %d sao cho bạn", donator.getDisplayName(), order.getAmount())
                )
                .user(user)
                .triggeredUserId(donator.getId())
                .build();
        repository.save(notification);
        stompService.pubNewNotification(notification);
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

    public void pushStartEvent(Livestream livestream) {
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

    public Notification markAsRead(Long id) {
        var notif = repository.findById(id).orElseThrow();
        notif.setIsRead(true);
        repository.save(notif);

        return notif;
    }
}
