package com.trio.java.bikerentapi.util;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import java.time.LocalDate;
import java.util.ArrayList;

public class ObjectsFactory {

  public static Bike createBike(int id, String name, Integer maxLoad) {
    return Bike.builder()
        .withId(id)
        .withName(name)
        .withImageUrls(new ArrayList<>())
        .withMaxLoad(maxLoad == null ? 0 : maxLoad)
        .build();
  }

  public static Bike createBike(int id, String name) {
    return createBike(id, name, null);
  }

  public static BikeRentalRequest createBikeRentalRequest(int bikeId, int rentalDays,
                                                          double riderWeight,
                                                          LocalDate startDate) {
    BikeRentalRequest request = new BikeRentalRequest();
    request.setBikeId(bikeId);
    request.setRentalDays(rentalDays);
    request.setRiderWeight(riderWeight);
    request.setStartDate(startDate);
    return request;
  }

  public static BikeRental createBikeRental(Integer bikeRentalId, double totalFee, int rentalDays,
                                            Bike bike, LocalDate startDate) {
    return BikeRental.builder()
        .withId(bikeRentalId)
        .withTotalFee(totalFee)
        .withTotalDays(rentalDays)
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(startDate.plusDays(rentalDays))
        .build();
  }

  public static BikeRental createBikeRentalWithoutId(int rentalDays, Bike bike,
                                                     LocalDate startDate) {
    return BikeRental.builder()
        .withTotalFee(100)
        .withTotalDays(rentalDays)
        .withBike(bike)
        .withStartDate(startDate)
        .withEndDate(startDate.plusDays(rentalDays))
        .build();
  }

  public static BikeRentalRequest createEmptyBikeRentalRequest() {
    return new BikeRentalRequest();
  }
}
