package org.example.dto;
import org.example.entity.Brand;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class VehicleForEnterpriseDto implements Serializable {
    Long id;
    LocalDate releaseYear;
    String carNumber;
    BigDecimal mileage;
    BigDecimal price;
    Long brandId;
    Long activeDriver;

    public VehicleForEnterpriseDto() {
    }

    public VehicleForEnterpriseDto(Long id, LocalDate releaseYear, String carNumber, BigDecimal mileage, BigDecimal price, Long brandId, Long activeDriver) {
        this.id = id;
        this.releaseYear = releaseYear;
        this.carNumber = carNumber;
        this.mileage = mileage;
        this.price = price;
        this.brandId = brandId;
        this.activeDriver = activeDriver;
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

    public Long getActiveDriver() {
        return activeDriver;
    }

    public void setActiveDriver(Long activeDriver) {
        this.activeDriver = activeDriver;
    }
}
