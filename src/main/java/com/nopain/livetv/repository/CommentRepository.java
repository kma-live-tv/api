package com.nopain.livetv.repository;

import com.nopain.livetv.model.Comment;
import com.nopain.livetv.model.Livestream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
}
