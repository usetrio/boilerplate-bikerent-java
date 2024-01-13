package com.trio.java.bikerentapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record BikeRentalResponse(Integer id, BikeResponse rentedBike, Double totalFee,
                                 @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
}
