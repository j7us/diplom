package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "driver_vehicle")
public class DriverVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    Driver driver;

    @Column(name = "active")
    Boolean active = false;

    public DriverVehicle() {
    }

    public DriverVehicle(Long id, Vehicle vehicle, Driver driver, Boolean active) {
        this.id = id;
        this.vehicle = vehicle;
        this.driver = driver;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
