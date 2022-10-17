package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.dto.BikeDTO;
import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.mapper.BikeMapper;
import com.trio.java.bikerentapi.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;


    private BikeMapper bikeMapper = new BikeMapper();

    @GetMapping
    public List<BikeDTO> findAll(){
        return bikeService.getAllBikes().stream()
                .map(b -> bikeMapper.fromBike(b))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public BikeDTO findById(@PathVariable("id") int id) {
        return bikeMapper.fromBike(bikeService.getBikeDetails(id)
                .orElseThrow(BikeNotFoundException::new));
    }
}
