package com.trio.java.bikerentapi.exception;

import lombok.Getter;


@Getter
public class UnavailableBikeException extends RuntimeException {
  private final Integer bikeId;

  public UnavailableBikeException(Integer bikeId) {
    super(
        ("Bike with id %s is already rented by another user. "
            + "Please choose a different bike or rental period.")
            .formatted(bikeId));
    this.bikeId = bikeId;
  }

}
