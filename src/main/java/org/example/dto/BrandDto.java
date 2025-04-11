package org.example.dto;

import java.math.BigDecimal;

public class BrandDto {
    Long id;

    String name;

    String carType;

    Integer capacity;

    String drive;

    BigDecimal weight;

    public BrandDto() {
    }

    public BrandDto(Long id, String name, String carType, Integer capacity, String drive, BigDecimal weight) {
        this.id = id;
        this.name = name;
        this.carType = carType;
        this.capacity = capacity;
        this.drive = drive;
        this.weight = weight;
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
