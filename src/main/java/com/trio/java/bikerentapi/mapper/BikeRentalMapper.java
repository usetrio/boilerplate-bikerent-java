package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.dto.response.BikeRentalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BikeRentalMapper {

  private final BikeMapper bikeMapper;

  public BikeRentalResponse fromBikeRental(BikeRental bikeRental) {
    return new BikeRentalResponse(
        bikeRental.getId(),
        bikeMapper.fromBike(bikeRental.getBike()),
        bikeRental.getTotalFee(),
        bikeRental.getStartDate(),
        bikeRental.getEndDate()
    );
  }
}
