package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.BikeRental;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySqlBikeRentalRepository extends JpaRepository<BikeRental, Integer> {
  List<BikeRental> findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(
      Integer bikeId, LocalDate localDate);
}
