package com.trio.java.bikerentapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


class BikeRentalControllerTest extends AbstractControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldRentBikeWhenEverythingIsOk() throws Exception {
    val bikeId = 1;
    val bikeRentalId = 1;
    val bike = ObjectsFactory.createBike(bikeId, "Some bike");
    val rentalDays = 5;
    val riderWeight = 70.0;
    val totalFee = 72.75;
    val bikeRental =
        ObjectsFactory.createBikeRental(bikeRentalId, totalFee, rentalDays, bike, LocalDate.now());
    when(bikeRentalService.rentBike(any())).thenReturn(bikeRental);
    BikeRentalRequest bikeRentalRequest =
        ObjectsFactory.createBikeRentalRequest(bikeId, rentalDays, riderWeight,
            LocalDate.now());
    mockMvc.perform(post("/api/bikes/rent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bikeRentalRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(bikeRentalId)))
        .andExpect(jsonPath("$.rentedBike.id", equalTo(bikeId)))
        .andExpect(jsonPath("$.startDate",
            equalTo(LocalDate.now().format(DateTimeFormatter.ISO_DATE))))
        .andExpect(jsonPath("$.endDate",
            equalTo(LocalDate.now().plusDays(rentalDays).format(DateTimeFormatter.ISO_DATE))))
        .andExpect(jsonPath("$.totalFee", equalTo(bikeRental.getTotalFee())));
  }

  @Test
  void shouldNotRentBikeWhenRequiredDataIsMissing() throws Exception {
    BikeRentalRequest bikeRentalRequest = ObjectsFactory.createEmptyBikeRentalRequest();
    mockMvc.perform(post("/api/bikes/rent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bikeRentalRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].message", equalTo("startDate must not be null")))
        .andExpect(jsonPath("$[1].message", equalTo("rentalDays must not be null")))
        .andExpect(jsonPath("$[2].message", equalTo("bikeId must not be null")))
        .andExpect(jsonPath("$[3].message", equalTo("riderWeight must not be null")));
  }

  @Test
  void shouldNotRentBikeWhenStartDateIsBeforeNow() throws Exception {
    val bikeId = 1;
    val rentalDays = 5;
    val riderWeight = 70.0;
    BikeRentalRequest bikeRentalRequest =
        ObjectsFactory.createBikeRentalRequest(bikeId, rentalDays, riderWeight,
            LocalDate.now().minusDays(1));
    mockMvc.perform(post("/api/bikes/rent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bikeRentalRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].message",
            equalTo("startDate must be today or someday after today")));
  }
}
