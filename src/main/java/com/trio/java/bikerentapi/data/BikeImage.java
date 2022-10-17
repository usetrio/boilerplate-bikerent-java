package com.trio.java.bikerentapi.data;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bike_images")
public class BikeImage {
    @Id
    private long id;
    private String url;
}
