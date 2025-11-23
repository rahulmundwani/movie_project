package com.bookmyshow.movie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "shows")
public class Show {

    @Id
    Long id;
    @NotNull
    Long movieId;
    @NotNull
    Long theatreId;
    LocalDateTime showTime;
    int totalSeats;
    int availableSeats;

    @Version
    private Long version;
}
