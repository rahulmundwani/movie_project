package com.bookmyshow.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {

    long id;
    String title;
    String status;

}
