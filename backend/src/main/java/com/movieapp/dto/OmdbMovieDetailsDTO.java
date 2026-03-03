package com.movieapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbMovieDetailsDTO {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Response")
    private String response;
}
