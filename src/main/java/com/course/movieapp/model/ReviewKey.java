package com.course.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReviewKey implements Serializable {

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "content_id", nullable = false)
    private int contentId;
}
