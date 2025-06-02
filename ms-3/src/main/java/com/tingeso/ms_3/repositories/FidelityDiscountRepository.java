package com.tingeso.ms_3.repositories;

import com.tingeso.ms_3.entities.FidelityDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FidelityDiscountRepository extends JpaRepository<FidelityDiscountEntity, Long> {
    List<FidelityDiscountEntity> findAllByMinPointsLessThanEqual(int points);
}
