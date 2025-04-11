package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class VehicleDto {
    Long id;

    LocalDate releaseYear;

    String carNumber;

    BigDecimal mileage;

    BigDecimal price;

    Long brandId;

    Long activeDriverId;

    public VehicleDto() {
    }

    public VehicleDto(Long id, LocalDate releaseYear, String carNumber, BigDecimal mileage, BigDecimal price, Long brandId, Long activeDriverId) {
        this.id = id;
        this.releaseYear = releaseYear;
        this.carNumber = carNumber;
        this.mileage = mileage;
        this.price = price;
        this.brandId = brandId;
        this.activeDriverId = activeDriverId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public BigDecimal getMileage() {
        return mileage;
    }

    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getActiveDriverId() {
        return activeDriverId;
    }

    public void setActiveDriverId(Long activeDriverId) {
        this.activeDriverId = activeDriverId;
    }
}
