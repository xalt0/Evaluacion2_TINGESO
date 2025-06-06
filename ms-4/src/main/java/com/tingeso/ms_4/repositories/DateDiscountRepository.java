package com.tingeso.ms_4.repositories;

import com.tingeso.ms_4.entities.DateDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DateDiscountRepository extends JpaRepository<DateDiscountEntity, Long> {
    List<DateDiscountEntity> findByScheduleDate(LocalDate date);
}
