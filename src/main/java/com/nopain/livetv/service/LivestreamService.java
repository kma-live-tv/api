package com.nopain.livetv.service;

import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.repository.LivestreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivestreamService {
    private final LivestreamRepository repository;

    public Livestream findByStreamKey(String streamKey) {
        return repository.findLivestreamByStreamKey(streamKey).orElseThrow();
    }
}
