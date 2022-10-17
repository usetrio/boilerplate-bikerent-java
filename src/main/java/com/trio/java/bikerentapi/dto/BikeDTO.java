package com.trio.java.bikerentapi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BikeDTO {
    private int id;
    private String name;
    private String type;
    private int bodySize;
    private int maxLoad;
    private double rate;
    private String description;
    private double ratings;
    private List<String> imageUrls;
}
