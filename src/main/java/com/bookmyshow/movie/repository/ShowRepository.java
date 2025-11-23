package com.bookmyshow.movie.repository;

import com.bookmyshow.movie.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<Long, Show> {

    boolean existsByMovieId(long movieId);

    Optional<Show> findShowByMovieId(long movieId);
}
