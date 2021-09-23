package com.course.movieapp.model;

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
@Table(name = "movie_role")
public class MovieRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieRoleId;

    @Column(name = "name", nullable = false)
    private String name;
}
