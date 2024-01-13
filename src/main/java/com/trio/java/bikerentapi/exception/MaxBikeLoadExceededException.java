package com.trio.java.bikerentapi.exception;

import lombok.Getter;


@Getter
public class MaxBikeLoadExceededException extends RuntimeException {
  private final Integer bikeId;
  private final Integer maxLoad;

  public MaxBikeLoadExceededException(Integer bikeId, Integer maxLoad) {
    super("Bike with id %s can only supports %s max load.".formatted(bikeId, maxLoad));
    this.bikeId = bikeId;
    this.maxLoad = maxLoad;
  }

}
