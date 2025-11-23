package com.bookmyshow.movie.service;

import com.bookmyshow.movie.dto.SeatReservationRequest;
import com.bookmyshow.movie.dto.SeatReservationResponse;
import com.bookmyshow.movie.exceptions.BusinessValidationException;
import com.bookmyshow.movie.exceptions.InsufficientSeatsException;
import com.bookmyshow.movie.exceptions.ShowNotFoundException;
import com.bookmyshow.movie.model.Show;
import com.bookmyshow.movie.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShowService {

    public final ShowRepository showRepository;

    public boolean getShowByMovieId(long movieId) {

        if (!showRepository.existsByMovieId(movieId)) {
            throw new BusinessValidationException("movieId",
                    String.valueOf(movieId),
                    "Movie cannot be deleted while shows are active");
        }
        return true;
    }

    @Transactional
    public SeatReservationResponse getShowDetails(long showId, SeatReservationRequest seatReservationRequest) {

        Show show = showRepository.findById(showId)
                .orElseThrow(ShowNotFoundException::new);

        if (seatReservationRequest.getBookedSeats() <= 0) {
            throw new BusinessValidationException("bookedSeats", String.valueOf(seatReservationRequest.getBookedSeats()),
                    "Booked seats must be greater than 0");
        }

        if (show.getTotalSeats() < seatReservationRequest.getBookedSeats()) {
            throw new BusinessValidationException("availableSeats",
                    String.valueOf(show.getAvailableSeats()),
                    "Not enough seats available");
        }

        show.setAvailableSeats(show.getAvailableSeats() - seatReservationRequest.getBookedSeats());

        Show updatedShow = showRepository.save(show);

        return SeatReservationResponse.builder()
                .showId(updatedShow.getId())
                .availableSeats(show.getAvailableSeats())
                .status("RESERVED")
                .build();
    }

}
