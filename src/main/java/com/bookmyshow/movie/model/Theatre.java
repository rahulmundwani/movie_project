package com.bookmyshow.movie.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class Theatre {

    @Id
    Long id;
    @NotNull
    String name;
    @NotNull
    String city;
    int totalScreens;
}
