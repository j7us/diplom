package org.example.repository;

import org.example.entity.Driver;
import org.example.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    List<Driver> findAllByEnterpriseIn(List<Enterprise> enterprises);

    Driver findByEnterpriseInAndId(List<Enterprise> enterprises, UUID id);
}
