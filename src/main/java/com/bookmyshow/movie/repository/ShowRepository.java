package com.bookmyshow.movie.repository;

import com.bookmyshow.movie.model.Show;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsByMovieId(long movieId);

    Optional<Show> findShowByMovieId(long movieId);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Show> findById(long showId);
}
