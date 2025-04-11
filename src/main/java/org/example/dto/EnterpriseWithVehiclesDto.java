package org.example.dto;

import java.util.List;

public class EnterpriseWithVehiclesDto {
    Long id;
    String name;
    String city;
    Integer productionCapacity;
    List<Integer> vehicles;

    public EnterpriseWithVehiclesDto() {
    }

    public EnterpriseWithVehiclesDto(Long id, String name, String city, Integer productionCapacity, List<Integer> vehicles) {
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

    public List<Integer> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Integer> vehicles) {
        this.vehicles = vehicles;
    }
}
