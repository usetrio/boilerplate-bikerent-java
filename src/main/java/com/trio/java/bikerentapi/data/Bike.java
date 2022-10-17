package com.trio.java.bikerentapi.data;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    private int id;
    private String name;
    private String type;
    private int bodySize;
    private int maxLoad;
    private double rate;
    private String description;
    private double ratings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="bike_id")
    private List<BikeImage> imageUrls;

}
