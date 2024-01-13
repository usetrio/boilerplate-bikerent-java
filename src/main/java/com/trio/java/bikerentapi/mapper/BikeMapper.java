package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.data.BikeImage;
import com.trio.java.bikerentapi.dto.response.BikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BikeMapper {

  public BikeResponse fromBike(Bike bike) {
    return BikeResponse.builder()
        .withId(bike.getId())
        .withName(bike.getName())
        .withType(bike.getType())
        .withBodySize(bike.getBodySize())
        .withMaxLoad(bike.getMaxLoad())
        .withRate(bike.getRate())
        .withDescription(bike.getDescription())
        .withRatings(bike.getRatings())
        .withImageUrls(
            bike.getImageUrls().stream()
                .map(BikeImage::getUrl)
                .toList()
        )
        .build();
  }
}
