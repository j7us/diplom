package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Brand {

    @Id
    @Column(columnDefinition = "uuid")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "car_type")
    String carType;

    @Column(name = "capacity")
    Integer capacity;

    @Column(name = "drive")
    String drive;

    @Column(name = "weight")
    BigDecimal weight;
}
