package org.example.repository;

import org.example.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {

    @Query(value = """
                WITH restr AS (
                    SELECT
                        enterprise_id AS ids
                    FROM
                        manager_enterprise
                    WHERE
                        manager_id = (SELECT id FROM manager WHERE username = :username)
                )
                SELECT
                    *
                FROM
                    enterprise
                WHERE
                    enterprise.id IN (SELECT ids FROM restr);
            """, nativeQuery = true)
    List<Enterprise> getAllWithUserRestriction(@Param(value = "username") String username);

    @Query(value = """
                WITH restr AS (
                    SELECT
                        manager_enterprise.enterprise_id AS id
                    FROM
                        manager_enterprise
                    WHERE
                        manager_id = (SELECT id FROM manager WHERE username = :username)
                        AND manager_enterprise.enterprise_id = :id
                )
                SELECT
                    *
                FROM
                    enterprise e
                WHERE
                    exists(SELECT 1 FROM restr) and e.id = (SELECT id from restr);
            """, nativeQuery = true)
    Optional<Enterprise> getByIdWithUserRestriction(@Param(value = "username") String username, @Param(value = "id") UUID id);

    Optional<Enterprise> getEnterpriseById( UUID id);
}
