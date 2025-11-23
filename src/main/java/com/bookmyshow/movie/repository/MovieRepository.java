package com.bookmyshow.movie.repository;

import com.bookmyshow.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {


    List<Movie> findByGenreAndLanguage(String genre, String language);

    @Query("Select m from Movie m WHERE"
            + ":genre is NULL OR m.genre = :genre"
            + ":language is NULL OR m.language = :language"
            + ":year is NULL OR YEAR(m.releaseDate) = :year")
    List<Movie> searchMovies(String genre, String language, int year);
}
