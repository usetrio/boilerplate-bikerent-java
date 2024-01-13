package com.trio.java.bikerentapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.MaxBikeLoadExceededException;
import com.trio.java.bikerentapi.exception.UnavailableBikeException;
import com.trio.java.bikerentapi.repository.BikeRentalRepository;
import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class BikeRentalServiceTest {

  @MockBean
  private BikeRepository bikeRepository;

  @MockBean
  private BikeRentalRepository bikeRentalRepository;

  @Autowired
  private BikeRentalService bikeRentalService;

  @Test
  void shouldNotRentBikeWhenBikeDetailsNotPresent() {
    int id = 404;
    int howManyDays = 2;
    BikeRentalRequest request = new BikeRentalRequest();
    request.setBikeId(id);
    request.setRentalDays(howManyDays);
    request.setRiderWeight(50.00);
    request.setStartDate(LocalDate.now());
    when(bikeRepository.getBikeDetails(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(BikeNotFoundException.class,
        () -> bikeRentalService.rentBike(request));
  }

  @Test
  void shouldNotRentBikeWhenRentalPeriodIsInvalid() {
    int bikeRentalId = 555;
    int bikeId = 308;
    int rentalDays = 2;
    Bike foundBike = ObjectsFactory.createBike(bikeId, "Some bike", null);
    LocalDate startDate = LocalDate.now();
    double riderWeight = 2;
    BikeRental bikeRental =
        ObjectsFactory.createBikeRental(bikeRentalId, 0.0, rentalDays, foundBike, startDate);
    when(bikeRepository.getBikeDetails(bikeId)).thenReturn(Optional.of(foundBike));
    BikeRental existingRental =
        ObjectsFactory.createBikeRental(bikeRentalId, 0.0, rentalDays, foundBike, startDate);
    when(
        bikeRentalRepository
            .findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(bikeId,
                startDate)).thenReturn(List.of(existingRental));
    when(bikeRentalRepository.save(any())).thenReturn(bikeRental);
    BikeRentalRequest request =
        ObjectsFactory.createBikeRentalRequest(bikeId, rentalDays, riderWeight, startDate);
    Assertions.assertThrows(UnavailableBikeException.class,
        () -> bikeRentalService.rentBike(request));
  }

  @Test
  void shouldNotRentBikeWhenBikeMaxLoadExceeds() {
    int bikeRentalId = 555;
    int bikeId = 308;
    int rentalDays = 2;
    Bike foundBike = ObjectsFactory.createBike(bikeId, "Some bike", 30);
    LocalDate startDate = LocalDate.now();
    double riderWeight = 50;
    when(bikeRepository.getBikeDetails(bikeId)).thenReturn(Optional.of(foundBike));
    when(
        bikeRentalRepository
            .findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(bikeId,
                startDate)).thenReturn(List.of());
    BikeRental bikeRental =
        ObjectsFactory.createBikeRental(bikeRentalId, 0.0, rentalDays, foundBike, startDate);
    when(bikeRentalRepository.save(any())).thenReturn(bikeRental);
    BikeRentalRequest request =
        ObjectsFactory.createBikeRentalRequest(bikeId, rentalDays, riderWeight, startDate);
    Assertions.assertThrows(MaxBikeLoadExceededException.class,
        () -> bikeRentalService.rentBike(request));
  }

  @Test
  void shouldRentBike() {
    int bikeRentalId = 555;
    int bikeId = 308;
    int rentalDays = 2;
    Bike foundBike = ObjectsFactory.createBike(bikeId, "Some bike", 150);
    LocalDate startDate = LocalDate.now();
    double riderWeight = 150;
    BikeRentalRequest request =
        ObjectsFactory.createBikeRentalRequest(bikeId, rentalDays, riderWeight, startDate);
    BikeRental bikeRental =
        ObjectsFactory.createBikeRental(bikeRentalId, 0.0, rentalDays, foundBike, startDate);
    when(bikeRepository.getBikeDetails(bikeId)).thenReturn(Optional.of(foundBike));
    when(
        bikeRentalRepository
            .findAllBikeRentalByBikeIdEqualsAndEndDateGreaterThanOrderByStartDateAsc(bikeId,
                startDate)).thenReturn(List.of());
    when(bikeRentalRepository.save(any())).thenReturn(bikeRental);
    var result = bikeRentalService.rentBike(request);
    Assertions.assertEquals(bikeRentalId, result.getId());
    Assertions.assertEquals(rentalDays, result.getTotalDays());
    Assertions.assertEquals(bikeId, result.getBike().getId());
  }
}
