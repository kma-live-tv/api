package com.nopain.livetv.repository;

import com.nopain.livetv.model.Livestream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Long>, JpaSpecificationExecutor<Livestream> {
    Optional<Livestream> findLivestreamByStreamKey(String streamKey);
}
