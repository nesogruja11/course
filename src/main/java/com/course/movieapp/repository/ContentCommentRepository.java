package com.course.movieapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentComment;

@Repository
public interface ContentCommentRepository extends JpaRepository<ContentComment, Integer> {

	long countByCommentIdAndReplayId(int commentId, int replayId);

	List<ContentComment> findByReplayId(int replayId);

	@Transactional
	@Modifying
	void deleteByContent(Content content);

	List<ContentComment> findByContent(Content content);
}
