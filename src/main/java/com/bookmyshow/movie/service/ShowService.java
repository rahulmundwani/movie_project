package com.bookmyshow.movie.service;

import com.bookmyshow.movie.exceptions.BusinessValidationException;
import com.bookmyshow.movie.repository.ShowRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    public ShowRepository showRepository;

    public boolean getShowByMovieId(long movieId) {

        if (!showRepository.existsByMovieId(movieId)) {
            throw new BusinessValidationException("movieId",
                    String.valueOf(movieId),
                    "Movie cannot be deleted while shows are active");
        }
        return true;
    }


}
