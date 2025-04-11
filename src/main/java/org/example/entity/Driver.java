package org.example.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "salary")
    BigDecimal salary;

    @Column(name = "work_experience")
    BigDecimal workExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprice_id")
    Enterprise enterprise;

    @OneToMany(mappedBy = "driver")
    List<DriverVehicle> vehicles = new ArrayList<>();

    public Driver() {
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

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<DriverVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<DriverVehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
