package com.trio.java.bikerentapi.dto.response;

import java.util.List;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record BikeResponse(int id, String name, String type, int bodySize, int maxLoad,
                           double rate, String description, double ratings,
                           List<String> imageUrls) {
}
