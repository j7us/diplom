package org.example.repository;

import org.example.entity.ManagerEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerEnterpriseRepository extends JpaRepository<ManagerEnterprise, Long> {

    List<ManagerEnterprise> findAllByManagerUsername(String username);

    void deleteAllByEnterpriseId(Long id);
}
