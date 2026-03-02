package com.movieapp.service;

import com.movieapp.model.Movie;
import com.movieapp.model.Rating;
import com.movieapp.model.User;
import com.movieapp.repository.MovieRepository;
import com.movieapp.repository.RatingRepository;
import com.movieapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public void rateMovie(Long movieId, Double score,String username) {
        if(score <= 0||score > 10){
            throw new RuntimeException("Score must be from 1 to 10");
        }
        Movie movie= movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found") );
        User user= userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Movie not found"));

        Optional<Rating> existingRating = ratingRepository.findByUserAndMovie(user,movie);
        if(existingRating.isPresent()){
            //update scenario
            Double oldScore = existingRating.get().getScore();
            existingRating.get().setScore(score);
            updateAverageOnUpdate(movie, oldScore, score);
        }
        else {
            //new rating
            Rating rating=new Rating();
            rating.setUser(user);
            rating.setMovie(movie);
            rating.setScore(score);
            ratingRepository.save(rating);

            updateAverageOnCreate(movie,score);
        }
        movieRepository.save(movie);
    }
    private void updateAverageOnCreate(Movie movie, Double newScore) {
        Double oldAvg=movie.getAverageRating();
        Integer count=movie.getTotalRatings();

        Double newAvg=((oldAvg*count)+newScore)/(count+1);

        movie.setAverageRating(newAvg);
        movie.setTotalRatings(count+1);
    }
    private void updateAverageOnUpdate(Movie movie,Double oldScore,Double newScore) {
        Double avg=movie.getAverageRating();
        Integer count=movie.getTotalRatings();

        Double newAvg=((avg * count) - oldScore + newScore) / count;

        movie.setAverageRating(newAvg);
    }
}
