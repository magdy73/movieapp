package com.movieapp.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
public class BatchMovieRequest {
    private List<String> ImdbIds;
}
