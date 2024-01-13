package com.trio.java.bikerentapi.controller;

import com.trio.java.bikerentapi.mapper.BikeMapper;
import com.trio.java.bikerentapi.mapper.BikeRentalMapper;
import com.trio.java.bikerentapi.service.BikeRentalService;
import com.trio.java.bikerentapi.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class AbstractControllerTest {
  @Autowired
  protected MockMvc mockMvc;

  @SpyBean
  protected BikeRentalMapper bikeRentalMapper;

  @SpyBean
  protected BikeMapper bikeMapper;

  @MockBean
  protected BikeService bikeService;

  @MockBean
  protected BikeRentalService bikeRentalService;

}
