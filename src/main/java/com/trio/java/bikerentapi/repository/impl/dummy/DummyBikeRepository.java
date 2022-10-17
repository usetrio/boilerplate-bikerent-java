package com.trio.java.bikerentapi.repository.impl.dummy;

import com.trio.java.bikerentapi.data.Bike;
import com.trio.java.bikerentapi.repository.BikeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DummyBikeRepository implements BikeRepository {
    private List<Bike> bikeList;
    public DummyBikeRepository(){
        bikeList = Arrays.asList(
                createBike(1, "Bike1", "Road Bike"),
                createBike(2, "Bike2", "Mountain Bike"),
                createBike(3, "Bike3", "City Bike")
        );
    }

    @Override
    public List<Bike> getAllBikes() {
        return this.bikeList;
    }

    @Override
    public Optional<Bike> getBikeDetails(int id) {
        return this.bikeList.stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }

    private Bike createBike(int id, String name, String type){
        return Bike.builder()
                .withId(id)
                .withName(name)
                .withType(type)
                .build();
    }
}
