package org.example.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DriverDto {
    Long id;
    String name;
    BigDecimal salary;
    BigDecimal workExperience;

    public DriverDto() {
    }

    public DriverDto(Long id, String name, BigDecimal salary, BigDecimal workExperience) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.workExperience = workExperience;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(BigDecimal workExperience) {
        this.workExperience = workExperience;
    }
}
