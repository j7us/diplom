package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vehicle {

    @Id
    @Column(columnDefinition = "uuid")
    UUID id;

    @Column(name = "release_year")
    LocalDate releaseYear;

    @Column(name = "car_number")
    String carNumber;

    @Column(name = "mileage")
    BigDecimal mileage;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "production_date")
    LocalDateTime productionDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToMany(mappedBy = "vehicle")
    List<DriverVehicle> drivers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprice_id")
    Enterprise enterprise;
}
