package com.movieapp.controller;

import com.movieapp.dto.OmdbSearchResponse;
import com.movieapp.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/omdb")
@RequiredArgsConstructor
public class OmdbController {

    private final OmdbService omdbService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public OmdbSearchResponse search(@RequestParam String title) {
        return omdbService.searchMovies(title);
    }
}
