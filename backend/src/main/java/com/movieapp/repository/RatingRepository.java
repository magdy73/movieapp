package com.movieapp.repository;

import com.movieapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Long, Rating> {

    Optional<Rating> findByUserAndMovie(User user, Movie movie);
}
