package com.nopain.livetv.service;

import com.nopain.livetv.dto.CommentResponse;
import com.nopain.livetv.dto.ReactionResponse;
import com.nopain.livetv.dto.stomp.DvrDoneMessage;
import com.nopain.livetv.dto.stomp.ViewsCountUpdatedMessage;
import com.nopain.livetv.mapper.NotificationMapper;
import com.nopain.livetv.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StompService {
    private final SimpMessagingTemplate messagingTemplate;

    public void pubNewNotification(Notification notification) {
        var user = notification.getUser();


        pubToUser(
                user.getId(),
                "/notifications",
                NotificationMapper.INSTANCE.toResponse(notification)
        );
        log.info("STOMP pubNewNotification");
        log.info(
                String.format(
                        "Notification to user %d - %s: %s",
                        user.getId(),
                        user.getUsername(),
                        notification.getContent()
                )
        );
    }

    public void pubNewReaction(Long livestreamId, ReactionResponse reaction) {
        pub(
                String.format("/livestreams/%d/reactions", livestreamId),
                reaction
        );

        log.info("STOMP pubNewReaction");
        log.info(
                String.format(
                        "Reaction to livestream %d",
                        livestreamId
                )
        );
    }

    public void pubNewComment(Long livestreamId, CommentResponse comment) {
        pub(
                String.format("/livestreams/%d/comments", livestreamId),
                comment
        );
    }

    public void pubEndLivestream(Long livestreamId) {
        pub(String.format("/livestreams/%d/end", livestreamId), livestreamId);
    }

    public void pubViewsCountChanged(Long livestreamId, int count) {
        pub(
                String.format("/livestreams/%d/views-count", livestreamId),
                ViewsCountUpdatedMessage
                        .builder()
                        .viewsCount(count)
                        .livestreamId(livestreamId)
                        .build()
        );
    }

    public void pubDvrDone(Long livestreamId, String file) {
        pub(
                String.format("/livestreams/%d/dvr", livestreamId),
                DvrDoneMessage
                        .builder()
                        .dvrFile(file)
                        .livestreamId(livestreamId)
                        .build()
        );
    }

    private void pub(String topic, Object payload) {
        messagingTemplate
                .convertAndSend(
                        String.format("/topic%s", topic),
                        payload
                );
    }

    private void pubToUser(Long userId, String topic, Object payload) {
        pub(
                String.format("/users/%d%s", userId, topic),
                payload
        );
    }
}
