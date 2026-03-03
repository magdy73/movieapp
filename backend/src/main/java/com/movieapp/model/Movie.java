package com.movieapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String imdbId;

    private String description;

    // جاي من OMDB
    private Double externalRating;

    // بتاع users عندنا
    private Double averageRating = 0.0;

    // عدد ال users اللي عملوا rating
    private Integer totalRatings = 0;

    private Integer releaseYear;
}
