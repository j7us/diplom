package org.example.repository;

import org.example.entity.ManagerEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ManagerEnterpriseRepository extends JpaRepository<ManagerEnterprise, Long> {

    List<ManagerEnterprise> findAllByManagerUsername(String username);

    void deleteAllByEnterpriseId(UUID id);
}
