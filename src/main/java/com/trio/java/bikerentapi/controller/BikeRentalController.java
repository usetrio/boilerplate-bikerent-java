package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import com.trio.java.bikerentapi.dto.response.BikeRentalResponse;
import com.trio.java.bikerentapi.mapper.BikeRentalMapper;
import com.trio.java.bikerentapi.service.BikeRentalService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bikes")
public class BikeRentalController {

  private final BikeRentalService bikeRentalService;
  private final BikeRentalMapper bikeRentalMapper;

  @PostMapping(value = "/rent")
  public ResponseEntity<BikeRentalResponse> rentBike(
      @Valid @RequestBody BikeRentalRequest bikeRentalRequest) {
    return ResponseEntity.ok()
        .body(bikeRentalMapper.fromBikeRental(bikeRentalService.rentBike(bikeRentalRequest)));
  }
}
