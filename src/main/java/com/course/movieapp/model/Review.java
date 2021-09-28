package com.course.movieapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Min(0)
    @Max(5)
    private int rating = 0;

    @Column(name = "favourite", nullable = true, columnDefinition = "boolean default false")
    private boolean favourite = false;
}
