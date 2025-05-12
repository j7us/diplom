package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "city")
    String city;

    @Column(name = "production_capacity")
    Integer productionCapacity;

    @OneToMany(mappedBy = "enterprise")
    List<Vehicle> vehicles;

    @OneToMany(mappedBy = "enterprise")
    List<Driver> drivers;

    public Enterprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getProductionCapacity() {
        return productionCapacity;
    }

    public void setProductionCapacity(Integer productionCapacity) {
        this.productionCapacity = productionCapacity;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
