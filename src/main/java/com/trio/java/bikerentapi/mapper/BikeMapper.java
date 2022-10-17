package com.trio.java.bikerentapi.mapper;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.dto.BikeDTO;

import java.util.stream.Collectors;

public class BikeMapper {

    public BikeDTO fromBike(Bike bike){
        return BikeDTO.builder()
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
                                .map(i -> i.getUrl())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
