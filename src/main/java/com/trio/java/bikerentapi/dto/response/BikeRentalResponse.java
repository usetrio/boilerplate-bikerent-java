package com.trio.java.bikerentapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trio.java.bikerentapi.dto.BikeDto;
import java.time.LocalDate;

public record BikeRentalResponse(Integer id, BikeDto rentedBike, Double totalFee,
                                 @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
}
