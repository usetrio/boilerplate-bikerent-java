package com.trio.java.bikerentapi.service;

import com.trio.java.bikerentapi.repository.BikeRepository;
import com.trio.java.bikerentapi.util.ObjectsFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
public class BikeServiceTest {

    @MockBean
    private BikeRepository bikeRepository;

    @Autowired
    private BikeService bikeService;



    @Test
    public void shouldReturnEmptyListIfNoBikesAvailable() {
        when(bikeRepository.getAllBikes()).thenReturn(new ArrayList<>());

        var result = bikeService.getAllBikes();
        assert(result.size() == 0);
    }

    @Test
    public void shouldReturnBikes() {
        when(bikeRepository.getAllBikes()).thenReturn(Arrays.asList(
                ObjectsFactory.createBike(1, "Bike1"),
                ObjectsFactory.createBike(2, "Bike2")
        ));

        var result = bikeService.getAllBikes();
        assert(result.size() == 2);
        assert(result.get(0).getId() == 1);
        assert(result.get(0).getName().equals("Bike1"));
        assert(result.get(1).getId() == 2);
        assert(result.get(1).getName().equals("Bike2") );
    }

    @Test
    public void shouldReturnBikeDetails() {
        int id = 308;

        when(bikeRepository.getBikeDetails(eq(id))).thenReturn(Optional.of(ObjectsFactory.createBike(id, "Some bike")));

        var result = bikeService.getBikeDetails(id);
        assert(result.isPresent());
        assert(result.get().getId() == id);
        assert(result.get().getName().equals("Some bike"));
    }

    @Test
    public void shouldReturnEmptyResponseIfBikeDetailsNotPresent() {
        int id = 404;

        when(bikeRepository.getBikeDetails(eq(id))).thenReturn(Optional.empty());

        var result = bikeService.getBikeDetails(id);
        assert(result.isEmpty());
    }


}
