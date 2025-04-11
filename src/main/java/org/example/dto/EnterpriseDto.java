package org.example.dto;

import java.util.List;

public class EnterpriseDto {
    Long id;
    String name;
    String city;
    Integer productionCapacity;
    List<Long> vehiclesId;
    List<Long> driversId;

    public EnterpriseDto() {
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

    public List<Long> getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(List<Long> vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    public List<Long> getDriversId() {
        return driversId;
    }

    public void setDriversId(List<Long> driversId) {
        this.driversId = driversId;
    }
}
