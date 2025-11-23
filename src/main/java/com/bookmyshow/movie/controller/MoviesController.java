package com.bookmyshow.movie.controller;

import com.bookmyshow.movie.dto.ErrorResponse;
import com.bookmyshow.movie.dto.MovieRequest;
import com.bookmyshow.movie.dto.MovieResponse;
import com.bookmyshow.movie.dto.SuccessResponse;
import com.bookmyshow.movie.exceptions.MovieNotFoundException;
import com.bookmyshow.movie.exceptions.ShowNotFoundException;
import com.bookmyshow.movie.model.Movie;
import com.bookmyshow.movie.service.MoviesService;
import com.bookmyshow.movie.service.ShowService;
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

    private final ShowService showService;

    @Autowired
    public MoviesController(MoviesService moviesService, ShowService showService) {
        this.moviesService = moviesService;
        this.showService = showService;
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
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId) {
        if (movieId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            MovieResponse movieResponse = moviesService.getMovieDetails(movieId);
            return ResponseEntity.status(HttpStatus.OK).body(movieResponse);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovieDetails(@PathVariable long movieId,
                                                @Valid @RequestBody MovieRequest movieRequest) {
        try {
            MovieResponse movieResponse = moviesService.updateMovie(movieId, movieRequest);
            return ResponseEntity.ok(movieResponse);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error("Movie Not Found.")
                    .timestamp(LocalDateTime.now()).build());
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable long movieId) {
        try {
            if (showService.getShowByMovieId(movieId)) {
                moviesService.deleteMovieById(movieId);
            }
        } catch (ShowNotFoundException ex) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam(value = "genre") String genre,
                                                            @RequestParam(value = "language") String language,
                                                            @RequestParam(value = "genre") int year) {

        List<Movie> movies = moviesService.searchMovies(genre, language, year);

        return ResponseEntity.ok(movies);

    }

}
