package com.nopain.livetv.repository;

import com.nopain.livetv.model.Livestream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Long>, JpaSpecificationExecutor<Livestream> {
}
