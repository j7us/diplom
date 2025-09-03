package org.example.repository;

import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, PagingAndSortingRepository<Vehicle, UUID> {

    void deleteAllByBrandId(UUID id);

    List<Vehicle> findAllByEnterpriseIn(List<Enterprise> enterprises);
    List<Vehicle> findAllByEnterprise(Enterprise enterprise);

    Page<Vehicle> findAllByEnterpriseIn(List<Enterprise> enterprises, Pageable pageable);
    Page<Vehicle> findAllByEnterprise(Enterprise enterprises, Pageable pageable);

    Optional<Vehicle> findByEnterpriseInAndId(List<Enterprise> enterprises, UUID id);

    void deleteByEnterpriseInOrEnterpriseIsNullAndId(List<Enterprise> enterprises, UUID id);
}
