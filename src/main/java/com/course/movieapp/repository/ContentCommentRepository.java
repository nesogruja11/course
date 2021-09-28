package com.course.movieapp.repository;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentCommentRepository extends JpaRepository<ContentComment, Integer> {

    long countByCommentIdAndReplayId(int commentId, int replayId);

    List<ContentComment> findByReplayId(int replayId);
}
