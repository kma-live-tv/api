package com.nopain.livetv.repository;

import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.LivestreamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Long>, JpaSpecificationExecutor<Livestream> {
    Optional<Livestream> findByStreamKey(String streamKey);

    int countByStatus(LivestreamStatus statuses);

    List<Livestream> findByStatus(LivestreamStatus status);
}
