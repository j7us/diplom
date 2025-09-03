package org.example.repository;

import org.example.entity.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long> {
    void deleteAllByVehicleId(UUID vehicleId);
}
