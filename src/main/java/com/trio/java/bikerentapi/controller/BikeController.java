package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.response.BikeResponse;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.mapper.BikeMapper;
import com.trio.java.bikerentapi.service.BikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bikes")
public class BikeController {
  private final BikeService bikeService;
  private final BikeMapper bikeMapper;

  @GetMapping
  public List<BikeResponse> findAll() {
    return bikeService.getAllBikes().stream()
        .map(bikeMapper::fromBike)
        .toList();
  }

  @GetMapping(value = "/{id}")
  public BikeResponse findById(@PathVariable("id") int id) {
    return bikeMapper.fromBike(bikeService.getBikeDetails(id)
        .orElseThrow(BikeNotFoundException::new));
  }
}
