package com.trio.java.bikerentapi.data;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "bike_rentals")
public class BikeRental {

  @Id
  @GeneratedValue
  private int id;
  @ManyToOne(optional = false)
  private Bike bike;
  private int totalDays;
  private LocalDate startDate;
  private LocalDate endDate;
  private double totalFee;

}
