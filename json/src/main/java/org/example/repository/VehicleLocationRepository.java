package org.example.repository;

import org.example.entity.Vehicle;
import org.example.entity.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, UUID>, JpaSpecificationExecutor<VehicleLocation> {

    List<VehicleLocation> findAllByVehicleAndDateBetween(Vehicle vehicle, LocalDateTime dateAfter, LocalDateTime dateBefore);

}
