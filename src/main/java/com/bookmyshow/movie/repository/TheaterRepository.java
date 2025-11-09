package com.bookmyshow.movie.repository;

import com.bookmyshow.movie.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Long, Theatre> {
}
