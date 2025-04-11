package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@NamedNativeQuery(
        name = "Enterprise.getWithVehicles",
        query = "select enterprise.id as id,\n" +
                "       enterprise.name as name,\n" +
                "       enterprise.city as city,\n" +
                "       enterprise.production_capacity as production_capacity,\n" +
                "       coalesce((SELECT json_agg(vehicle.id)\n" +
                "                         FROM vehicle\n" +
                "                                  left join driver_vehicle on vehicle.id = driver_vehicle.vehicle_id and driver_vehicle.active = true\n" +
                "                                  left join driver on driver_vehicle.driver_id = driver.id\n" +
                "                         where vehicle.enterprice_id = enterprise.id), json_build_array()) as vehicles\n" +
                "from\n" +
                "    enterprise;",
        resultSetMapping = "Enterprise.toDTOWithVehicles"
)
@SqlResultSetMapping(
        name = "Enterprise.toDTOWithVehicles",
        classes = @ConstructorResult(targetClass = EnterpriseWithVehicles.class,
        columns = {@ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "city", type = String.class),
                @ColumnResult(name = "production_capacity", type = Integer.class),
                @ColumnResult(name = "vehicles", type = String.class)})
)
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
