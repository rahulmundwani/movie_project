package com.bookmyshow.movie.controller;

import com.bookmyshow.movie.dto.SeatReservationRequest;
import com.bookmyshow.movie.dto.SeatReservationResponse;
import com.bookmyshow.movie.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/shows")
public class ShowController {

    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PatchMapping("/{showId}/reserve")
    public ResponseEntity<?> reserveSeats(@PathVariable long showId,
                                          @Valid @RequestBody SeatReservationRequest seatReservationRequest) {
        SeatReservationResponse seatReservationResponse =
                showService.getShowDetails(showId, seatReservationRequest);

        return ResponseEntity.ok(seatReservationResponse);
    }


}
