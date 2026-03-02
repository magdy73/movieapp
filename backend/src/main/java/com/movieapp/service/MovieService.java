package com.movieapp.service;

import com.movieapp.dto.OmdbMovieDetailsDTO;
import com.movieapp.model.Movie;
import com.movieapp.repository.MovieRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    public Page<Movie> getAllMovies(Pageable pageable){
        return movieRepository.findAll(pageable);
    }
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
    public Page<Movie> searchMovies(String keyword, Pageable pageable) {
        if(keyword==null||keyword.trim().isEmpty()) {
            return movieRepository.findAll(pageable);
        }
        return movieRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    public Movie updateMovie(Long id,Movie updatedMovie) {
        Movie movie = getMovieById(id);

        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setRating(updatedMovie.getRating());
        movie.setReleaseYear(updatedMovie.getReleaseYear());
        return movieRepository.save(movie);
    }
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie importMovieFromOmdb(OmdbMovieDetailsDTO dto){
        Movie movie = new Movie();
        if(movieRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new RuntimeException("Movie already exists");
        }
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getPlot());
        movie.setReleaseYear(Integer.parseInt(dto.getYear()));
        try {
            movie.setRating(Double.parseDouble(dto.getImdbRating()));
        } catch (Exception e) {
            movie.setRating(0.0);
        }
        return movieRepository.save(movie);
    }
}
