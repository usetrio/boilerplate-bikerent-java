package com.trio.java.bikerentapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trio.java.bikerentapi.validation.AfterNow;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class BikeRentalRequest {
  @NotNull
  private Integer bikeId;

  @NotNull
  private Integer rentalDays;

  @JsonFormat(pattern = "MM-dd-yyyy")
  @NotNull
  @AfterNow
  private LocalDate startDate;

  @NotNull
  private Double riderWeight;
}
