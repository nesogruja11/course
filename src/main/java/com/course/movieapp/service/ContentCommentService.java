package com.course.movieapp.service;

import com.course.movieapp.dto.ContentCommentDto;
import com.course.movieapp.model.ContentComment;
import com.course.movieapp.repository.ContentCommentRepository;
import com.course.movieapp.repository.ContentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentCommentService {

    @Autowired
    ContentCommentRepository contentCommentRepository;

    @Autowired
    ContentService contentService;

    @Autowired
    UserService userService;

    public ContentComment save(ContentComment contentComment){
        return contentCommentRepository.save(contentComment);
    }

    public ContentComment update(ContentCommentDto contentCommentDto) throws NotFoundException {
        if(checkIfCommentExist(contentCommentDto.getCommentId(), contentCommentDto.getReplayId())){
            return contentCommentRepository.save(buildContentCommentFromDto(contentCommentDto));
        }
        throw new NotFoundException("Nije pronađen komentar sa id-em:"+contentCommentDto.getCommentId());
    }

    public ContentComment findById(int id) throws NotFoundException {
        return contentCommentRepository.findById(id).orElseThrow(() -> new NotFoundException("Nije pronađen komentar sa id-em:"+id));
    }
/*
    public void delete(ContentCommentDto contentCommentDto) throws NotFoundException {
        if(checkIfCommentExist(contentCommentDto.getCommentId(), contentCommentDto.getReplayId())){
            List<ContentComment> childComments = contentCommentRepository.findByReplayId(contentCommentDto.getReplayId());
            childComments.forEach(comment ->{
                List<ContentComment> childComments2 = contentCommentRepository.findByReplayId(comment.getReplayId());
                childComments2.forEach(comment2 ->{
                    List<ContentComment> childComments3 = contentCommentRepository.findByReplayId(comment2.getReplayId());

                });
            });
        }
        throw new NotFoundException("Nije pronađen komentar sa id-em:"+contentCommentDto.getCommentId());
    }
*/
    private boolean checkIfCommentExist(int commentId, int replayId){
        return (contentCommentRepository.countByCommentIdAndReplayId(commentId, replayId) == 1 ? true : false);
    }

    private ContentComment buildContentCommentFromDto(ContentCommentDto contentCommentDto) throws NotFoundException {
        ContentComment contentComment = new ContentComment();
        contentComment.setComment(contentCommentDto.getComment());
        contentComment.setContent(contentService.findById(contentCommentDto.getContentId()));
        if(contentCommentDto.getReplayId()!=null)
        contentComment.setReplayId(contentCommentDto.getReplayId());
        contentComment.setUser(userService.findById(contentCommentDto.getUserId()));

        return contentComment;
    }
}
