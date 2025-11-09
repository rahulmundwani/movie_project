package com.bookmyshow.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    Long movieId;
    String title;
    String genre;
    String language;
    int durationInMin;
    LocalDate releaseDate;
}