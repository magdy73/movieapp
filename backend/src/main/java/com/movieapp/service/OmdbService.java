package com.movieapp.service;

import com.movieapp.dto.OmdbSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OmdbService {

    @Value("${omdb.api.key}")
    private String apiKey;
    @Value("${omdb.api.url}")
    private String apiUrl;
    
    private final RestTemplate restTemplate=new RestTemplate();

    public OmdbSearchResponse searchMovies(String title){
        String url= apiUrl + "?apiKey=" + apiKey + "&s=" +title;
        return restTemplate.getForObject(url,OmdbSearchResponse.class);
    }
}
