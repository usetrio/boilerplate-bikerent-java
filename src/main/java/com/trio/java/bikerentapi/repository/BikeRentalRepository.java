package com.trio.java.bikerentapi.repository;

import com.trio.java.bikerentapi.data.BikeRental;
import java.time.LocalDate;
import java.util.List;

public interface BikeRentalRepository {
  List<BikeRental> findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(
      Integer bikeId, LocalDate localDate);

  BikeRental save(BikeRental rental);
}
