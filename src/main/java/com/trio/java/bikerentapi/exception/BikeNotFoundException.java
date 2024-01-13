package com.trio.java.bikerentapi.exception;

public class BikeNotFoundException extends RuntimeException {
  public BikeNotFoundException() {
    super("Bike with id %s was not found.");
  }
}
