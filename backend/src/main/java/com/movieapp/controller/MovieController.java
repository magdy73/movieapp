package com.movieapp.controller;

import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateMovie/{id}")
    public Movie updateMovie(@PathVariable Long id,@RequestBody Movie movie) {
        return movieService.updateMovie(id,movie);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
