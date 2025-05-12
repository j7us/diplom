package org.example.repository;

import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    void deleteAllByBrandId(Long id);

    List<Vehicle> findAllByEnterpriseInOrEnterpriseIsNull(List<Enterprise> enterprises);

    Optional<Vehicle> findByEnterpriseInOrEnterpriseIsNullAndId(List<Enterprise> enterprises, Long id);

    void deleteByEnterpriseInOrEnterpriseIsNullAndId(List<Enterprise> enterprises, Long id);
}
