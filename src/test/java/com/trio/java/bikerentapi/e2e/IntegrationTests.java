package com.trio.java.bikerentapi.e2e;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.val;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class IntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldReturnBikes() throws Exception {
    this.mockMvc.perform(
            get("/api/bikes")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
  }

  @Test
  void shouldReturnBikeDetails() throws Exception {
    int id = 1;

    this.mockMvc.perform(
            get(String.format("/api/bikes/%s", id))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(id)))
        .andExpect(jsonPath("$.name", equalTo("Monahan and Sons")));
  }

  @Test
  void shouldReturnErrorForUnknownBike() throws Exception {
    int id = 404;
    this.mockMvc.perform(
            get(String.format("/api/bikes/%s", id))
        )
        .andExpect(status().isNotFound());
  }

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

}
