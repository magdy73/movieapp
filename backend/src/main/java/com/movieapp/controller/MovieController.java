package com.movieapp.controller;

import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import com.movieapp.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.*;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/allMovies")
    public Page<Movie> getAllMovies(@RequestParam(defaultValue = "0")int page,
        @RequestParam(defaultValue ="5")int size,
        @RequestParam(defaultValue = "id,asc")String [] sort)
    {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sort[0]).ascending());
        return movieService.getAllMovies(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getMovieById/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search")
    public Page<Movie> searchMovie(@RequestParam String keyword,Pageable pageable) {
        return movieService.searchMovies(keyword,pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{movieId}/rate")
    public ResponseEntity<?> rateMovie(
            @PathVariable Long movieId,
            @RequestParam Double score,
            Authentication authentication
    ) {

        ratingService.rateMovie(
                movieId,
                score,
                authentication.getName()
        );

        return ResponseEntity.ok("Movie rated successfully");
    }


}
