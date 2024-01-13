package com.trio.java.bikerentapi.service;

import com.trio.java.bikerentapi.data.BikeRental;
import com.trio.java.bikerentapi.dto.request.BikeRentalRequest;

public interface BikeRentalService {
  BikeRental rentBike(BikeRentalRequest bikeRentalRequest);
}
