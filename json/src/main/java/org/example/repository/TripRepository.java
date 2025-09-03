package org.example.repository;

import org.example.entity.Trip;
import org.example.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID>, JpaSpecificationExecutor<Trip> {
    
    List<Trip> findAllByVehicleAndStartDateAfterAndEndDateBefore(Vehicle vehicle, LocalDateTime startDateAfter, LocalDateTime endDateBefore);

    List<Trip> findAllByIdInOrderByEndDate(Collection<UUID> ids);
}
