package org.example.entity;

import java.io.Serializable;

public class EnterpriseWithVehicles implements Serializable {
    Long id;
    String name;
    String city;
    Integer productionCapacity;
    String vehicles;
    String drivers;

    public EnterpriseWithVehicles(Long id, String name, String city, Integer productionCapacity, String vehicles) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.productionCapacity = productionCapacity;
        this.vehicles = vehicles;
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

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }
}
