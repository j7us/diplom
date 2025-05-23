package org.example.repository;

import org.example.entity.Driver;
import org.example.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findAllByEnterpriseIn(List<Enterprise> enterprises);

    Driver findByEnterpriseInAndId(List<Enterprise> enterprises, Long id);
}
