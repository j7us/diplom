package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "enterprise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Enterprise {

    @Id
    @Column(columnDefinition = "uuid")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "city")
    String city;

    @Column(name = "production_capacity")
    Integer productionCapacity;

    @OneToMany(mappedBy = "enterprise")
    List<Vehicle> vehicles;

    @Column(name = "zone")
    String zone;

    @OneToMany(mappedBy = "enterprise")
    List<Driver> drivers;
}
