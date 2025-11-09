package com.bookmyshow.movie.controller;


import com.bookmyshow.movie.dto.ErrorResponse;
import com.bookmyshow.movie.dto.MovieRequest;
import com.bookmyshow.movie.dto.MovieResponse;
import com.bookmyshow.movie.dto.SuccessResponse;
import com.bookmyshow.movie.exceptions.MovieNotFoundException;
import com.bookmyshow.movie.service.MoviesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController("/movies")
public class MoviesController {

    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }


    @PostMapping
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieRequest movieRequest) {
        if (movieRequest.getTitle().isEmpty() || movieRequest.getDurationInMin() <= 0 || movieRequest.getLanguage().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().error("Validation failed").details(List.of(
                            "title must not be blank",
                            "durationInMin must be greater than or equal to 30",
                            "releaseDate must not be null"
                    )).timestamp(LocalDateTime.now()).build());
        }
        SuccessResponse movieResponse = moviesService.addMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponse);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId){
        if(movieId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try{
            MovieResponse movieResponse = moviesService.getMovieDetails(movieId);
            return ResponseEntity.status(HttpStatus.OK).body(movieResponse);
        }catch (MovieNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovieDetails ( @PathVariable long movieId,
                                                  @Valid @RequestBody MovieRequest movieRequest){
        try {
            MovieResponse movieResponse = moviesService.updateMovie(movieId, movieRequest);
            return ResponseEntity.ok(movieResponse);
        }catch (MovieNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error("Movie Not Found.")
                    .timestamp(LocalDateTime.now()).build());
        }
    }

}
