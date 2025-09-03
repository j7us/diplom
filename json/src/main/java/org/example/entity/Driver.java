package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Driver {

    @Id
    @Column(columnDefinition = "uuid")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "salary")
    BigDecimal salary;

    @Column(name = "work_experience")
    BigDecimal workExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprice_id")
    Enterprise enterprise;

    @OneToMany(mappedBy = "driver")
    List<DriverVehicle> vehicles = new ArrayList<>();
}
