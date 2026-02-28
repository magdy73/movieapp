package com.movieapp.controller;

import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }
    @GetMapping("/allMovies")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
    @GetMapping("/getMovieById/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }
    @PutMapping("/updateMovie/{id}")
    public Movie updateMovie(@PathVariable Long id,@RequestBody Movie movie) {
        return movieService.updateMovie(id,movie);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
