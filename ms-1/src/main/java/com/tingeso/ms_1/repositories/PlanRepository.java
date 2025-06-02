package com.tingeso.ms_1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tingeso.ms_1.entities.PlanEntity;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
    Optional<PlanEntity> findByName(String name);
}
