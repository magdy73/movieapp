package com.movieapp.controller;

import com.movieapp.dto.OmdbMovieDetailsDTO;
import com.movieapp.dto.OmdbSearchResponse;
import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import com.movieapp.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/omdb")
@RequiredArgsConstructor
public class OmdbController {

    private final OmdbService omdbService;
    private final MovieService movieService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public OmdbSearchResponse search(@RequestParam String title) {
        return omdbService.searchMovies(title);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/import")
    public Movie importMovie(@RequestParam String imdbId){
        OmdbMovieDetailsDTO dto=omdbService.getMovieByImdbId(imdbId);
        if (!"True".equalsIgnoreCase(dto.getResponse())) {
            throw new RuntimeException("Movie not found in OMDB");
        }
        return movieService.importMovieFromOmdb(dto);
    }


}
