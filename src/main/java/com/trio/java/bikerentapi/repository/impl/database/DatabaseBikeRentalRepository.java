package com.trio.java.bikerentapi.repository.impl.database;

import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.repository.BikeRentalRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseBikeRentalRepository implements BikeRentalRepository {

  @Autowired
  private MySqlBikeRentalRepository db;

  @Override
  public List<BikeRental> findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(
      Integer bikeId, LocalDate localDate) {
    return db.findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(bikeId,
        localDate);
  }

  @Override
  public BikeRental save(BikeRental rental) {
    return db.save(rental);
  }
}
