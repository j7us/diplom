package org.example.repository;

import org.example.entity.Enterprise;
import org.example.entity.EnterpriseWithVehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    @Query(nativeQuery = true)
    List<EnterpriseWithVehicles> getWithVehicles();
}
