package com.bookmyshow.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatReservationResponse {

    private Long showId;
    private int availableSeats;
    private String status;  // "RESERVED" or "FAILED"

}
