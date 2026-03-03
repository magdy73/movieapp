package com.movieapp.repository;

import com.movieapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);

    Page<Movie> findByTitleContainingIgnoreCase(String keyword,Pageable pageable);
    boolean existsByImdbId(String imdbId);
}
