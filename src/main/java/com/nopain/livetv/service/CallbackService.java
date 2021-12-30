package com.nopain.livetv.service;

import com.nopain.livetv.dto.callback.BaseStreamEvent;
import com.nopain.livetv.dto.callback.DvrEvent;
import com.nopain.livetv.dto.srs.AllStreamsResponse;
import com.nopain.livetv.dto.stomp.DvrDoneMessage;
import com.nopain.livetv.dto.stomp.ViewsCountUpdatedMessage;
import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.LivestreamStatus;
import com.nopain.livetv.properties.SrsProperties;
import com.nopain.livetv.repository.LivestreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackService {
    private final LivestreamService livestreamService;
    private final LivestreamRepository livestreamRepository;
    private final SrsProperties srsProperties;
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public void onEvent(BaseStreamEvent event) throws NotFoundException {
        var livestream = livestreamService.findByStreamKey(event.getStream());
        var action = event.getAction();

        switch (action) {
            case on_publish:
                onPublish(livestream);
                break;
            case on_unpublish:
                onUnPublish(livestream);
                break;
            case on_play:
            case on_stop:
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> updateViewsCount(livestream), 1, TimeUnit.SECONDS);
                break;
            case on_dvr:
                onDvr(livestream, ((DvrEvent) event).getFile());
                break;
            default:
                throw new NotFoundException("Action not supported");
        }
    }

    private void onPublish(Livestream livestream) {
        livestream.setStatus(LivestreamStatus.STREAMING);
        livestreamRepository.save(livestream);
    }

    private void onUnPublish(Livestream livestream) {
        livestream.setStatus(LivestreamStatus.END);
        livestream.setViewsCount(0);
        livestreamRepository.save(livestream);
    }

    private void updateViewsCount(Livestream livestream) {
        try {
            AllStreamsResponse allStreams = restTemplate.getForObject(
                    srsProperties.getApiUrl() + "/streams/",
                    AllStreamsResponse.class
            );

            if (allStreams == null) {
                return;
            }

            var streamInfo = allStreams
                    .getStreams()
                    .stream()
                    .filter(s -> s.getName().equals(livestream.getStreamKey()))
                    .findFirst()
                    .orElseThrow();
            var viewsCount = streamInfo.getClients();
            livestream.setViewsCount(viewsCount);
            livestreamRepository.save(livestream);

            messagingTemplate
                    .convertAndSend(
                            "/topic/livestreams/" + livestream.getId() + "/views-count",
                            ViewsCountUpdatedMessage
                                    .builder()
                                    .viewsCount(viewsCount)
                                    .livestreamId(livestream.getId())
                                    .build()
                    );
        } catch (Exception ex) {
            log.error(ex.toString());
        }
    }

    private void onDvr(Livestream livestream, String file) {
        String dvrFile = file.replace("./objs/nginx/html", "");
        livestream.setDvrFile(dvrFile);
        livestreamRepository.save(livestream);

        messagingTemplate
                .convertAndSend(
                        "/topic/livestreams/" + livestream.getId() + "/dvr",
                        DvrDoneMessage
                                .builder()
                                .dvrFile(dvrFile)
                                .livestreamId(livestream.getId())
                                .build()
                );
    }
}
