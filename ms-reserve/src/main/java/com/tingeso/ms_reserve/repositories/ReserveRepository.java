package com.tingeso.ms_reserve.repositories;

import com.tingeso.ms_reserve.entities.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {

    List<ReserveEntity> findByScheduleDateBetween(LocalDate startDate, LocalDate endDate);
}
