package com.course.movieapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "content_genre")
public class ContentGenre {

    @EmbeddedId
    @Column(name = "content_genre_id")
    private ContentGenreKey contentGenreId;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @MapsId("contentId")
    @JsonIgnore
    @JoinColumn(name = "content_id")
    private Content content;
}
