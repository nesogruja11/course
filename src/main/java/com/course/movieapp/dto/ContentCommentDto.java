package com.course.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentCommentDto {

    private int commentId;
    private int userId;
    private int contentId;
    private String comment;
    private Integer replayId;
}
