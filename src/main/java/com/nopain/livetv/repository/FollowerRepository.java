package com.nopain.livetv.repository;

import com.nopain.livetv.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long>, JpaSpecificationExecutor<Follower> {
    Optional<Follower> findByFollowedByIdAndFollowToId(Long followedById, Long followToId);
}
