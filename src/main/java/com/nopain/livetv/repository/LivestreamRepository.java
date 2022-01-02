package com.nopain.livetv.repository;

import com.nopain.livetv.model.Livestream;
import com.nopain.livetv.model.LivestreamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Long>, JpaSpecificationExecutor<Livestream> {
    List<Livestream> findByStatus(LivestreamStatus status);

    List<Livestream> findByUserId(Long userId);

    @Query(value = "SELECT lt FROM Livestream lt WHERE lt.status = 'WAITING' AND lt.waitingFrom < :timeoutAt")
    List<Livestream> findTimeoutLivestreams(@Param("timeoutAt") Instant timeoutAt);
}
