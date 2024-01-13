package com.trio.java.bikerentapi.service.impl;

import static java.time.LocalDate.now;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.MaxBikeLoadExceededException;
import com.trio.java.bikerentapi.exception.UnavailableBikeException;
import com.trio.java.bikerentapi.repository.BikeRentalRepository;
import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.service.BikeRentalService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BikeRentalServiceImpl implements BikeRentalService {
  private static final Double FIXED_RATE = 0.15;

  private final BikeRepository bikeRepository;
  private final BikeRentalRepository bikeRentalRepository;

  @Override
  public BikeRental rentBike(BikeRentalRequest bikeRentalRequest) {
    var foundBike = bikeRepository.getBikeDetails(bikeRentalRequest.getBikeId())
        .orElseThrow(BikeNotFoundException::new);
    validate(bikeRentalRequest, foundBike);
    var rental = buildBikeRental(bikeRentalRequest, foundBike);
    return bikeRentalRepository.save(rental);
  }

  private Double calculateTotalFee(Integer rentalDays, double bikeRate) {
    return rentalDays * bikeRate * FIXED_RATE;
  }

  private BikeRental buildBikeRental(BikeRentalRequest bikeRentalRequest, Bike foundBike) {
    var totalFee = calculateTotalFee(bikeRentalRequest.getRentalDays(), foundBike.getRate());
    return BikeRental.builder().withBike(foundBike).withStartDate(bikeRentalRequest.getStartDate())
        .withEndDate(bikeRentalRequest.getStartDate().plusDays(bikeRentalRequest.getRentalDays()))
        .withTotalDays(bikeRentalRequest.getRentalDays()).withTotalFee(totalFee).build();
  }

  private void validate(BikeRentalRequest bikeRentalRequest, Bike foundBike) {
    validateRentalPeriod(foundBike, bikeRentalRequest);
    validateBikeMaxLoad(foundBike, bikeRentalRequest);
  }

  private void validateBikeMaxLoad(Bike foundBike, BikeRentalRequest bikeRentalRequest) {
    if (foundBike.getMaxLoad() > 0 && bikeRentalRequest.getRiderWeight() > foundBike.getMaxLoad()) {
      throw new MaxBikeLoadExceededException(foundBike.getId(), foundBike.getMaxLoad());
    }
  }

  private void validateRentalPeriod(Bike foundBike, BikeRentalRequest bikeRentalRequest) {
    List<BikeRental> foundBikeRentals =
        bikeRentalRepository
            .findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(
                foundBike.getId(), now());
    foundBikeRentals.forEach(foundRental -> {
      if (isRentalPeriodInvalid(bikeRentalRequest, foundRental)) {
        throw new UnavailableBikeException(foundBike.getId());
      }
    });
  }

  private boolean isRentalPeriodInvalid(BikeRentalRequest bikeRentalRequest,
                                        BikeRental foundRental) {
    LocalDate requestedStartDate = bikeRentalRequest.getStartDate();
    LocalDate requestedEndDate = requestedStartDate.plusDays(bikeRentalRequest.getRentalDays());
    LocalDate existingStartDate = foundRental.getStartDate();
    LocalDate existingEndDate = existingStartDate.plusDays(foundRental.getTotalDays());
    return (requestedStartDate.isBefore(existingEndDate)
        && requestedEndDate.isAfter(existingStartDate));
  }

}
