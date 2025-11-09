package com.bookmyshow.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    String title;
    String genre;
    String language;
    int durationInMin;
    LocalDate releaseDate;

}
