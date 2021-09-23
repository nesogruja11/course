package com.course.movieapp.model;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @EmbeddedId
    @Column(name = "review_id")
    private ReviewKey reviewKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(name = "rating", nullable = true)
    private int rating;

    @Column(name = "favourite", nullable = true)
    private boolean favourite;
}
