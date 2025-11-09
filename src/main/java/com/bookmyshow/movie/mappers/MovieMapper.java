package com.bookmyshow.movie.mappers;

import com.bookmyshow.movie.dto.MovieResponse;
import com.bookmyshow.movie.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieResponse movieToMovieResponse(Movie movie);
}
