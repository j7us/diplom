package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trip")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Trip {

    @Id
    @Column(columnDefinition = "uuid")
    UUID id;

    @Column(name = "start_date_trip")
    LocalDateTime startDate;

    @Column(name = "end_date_trip")
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "start_location_id")
    VehicleLocation startLocation;

    @OneToOne
    @JoinColumn(name = "end_location_id")
    VehicleLocation endLocation;

    @Column(name = "distance")
    BigDecimal distance;
}
