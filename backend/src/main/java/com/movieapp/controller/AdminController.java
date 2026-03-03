package com.movieapp.controller;

import com.movieapp.dto.BatchMovieRequest;
import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import com.movieapp.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final MovieService movieService;
    private final RatingService ratingService;

    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @PutMapping("/updateMovie/{id}")
    public Movie updateMovie(@PathVariable Long id,@RequestBody Movie movie) {
        return movieService.updateMovie(id,movie);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @PostMapping("/addBatch")
    public ResponseEntity<?> batchAddMovies(@RequestBody BatchMovieRequest request) {

        movieService.batchAddMovies(request.getImdbIds());

        return ResponseEntity.ok("Movies added successfully");
    }

    @DeleteMapping("/deleteBatch")
    public ResponseEntity<?> batchDeleteMovies(@RequestBody List<Long> ids) {

        movieService.batchDeleteMovies(ids);

        return ResponseEntity.ok("Movies deleted successfully");
    }
}
