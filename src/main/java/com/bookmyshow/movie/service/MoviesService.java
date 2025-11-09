package com.bookmyshow.movie.service;

import com.bookmyshow.movie.dto.MovieRequest;
import com.bookmyshow.movie.dto.MovieResponse;
import com.bookmyshow.movie.dto.SuccessResponse;
import com.bookmyshow.movie.exceptions.MovieNotFoundException;
import com.bookmyshow.movie.mappers.MovieMapper;
import com.bookmyshow.movie.model.Movie;
import com.bookmyshow.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoviesService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Autowired
    public MoviesService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }


    public SuccessResponse addMovie(MovieRequest movieRequest){
        Movie movie = Movie.builder().title(movieRequest.getTitle()).genre(movieRequest.getGenre())
                        .durationInMin(movieRequest.getDurationInMin()).language(movieRequest.getLanguage())
                        .releaseDate(movieRequest.getReleaseDate()).build();
        Movie savedMovie =  movieRepository.save(movie);

        return SuccessResponse.builder().id(savedMovie.getMovieId()).title(savedMovie.getTitle())
                .status("CREATED").build();
    }

    public MovieResponse getMovieDetails(long id){

        Optional<Movie> movie = movieRepository.findById(id);

        if(movie.isEmpty()){
            throw new MovieNotFoundException();
        }

        return movieMapper.movieToMovieResponse(movie.get());
    }

    public MovieResponse updateMovie(long movieId,
                                     MovieRequest movieRequest){
        Optional<Movie> movie = movieRepository.findById(movieId);

        if(movie.isEmpty()){
            throw new MovieNotFoundException();
        }

        Movie updatedMovie = Movie.builder().movieId(movieId).title(movieRequest.getTitle()).genre(movieRequest.getGenre())
                .durationInMin(movieRequest.getDurationInMin()).language(movieRequest.getLanguage())
                .releaseDate(movieRequest.getReleaseDate()).build();

        return movieMapper.movieToMovieResponse(movieRepository.save(updatedMovie));
    }

}
