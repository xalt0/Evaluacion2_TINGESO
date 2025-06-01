package com.tingeso.ms_reserve.repositories;

import com.tingeso.ms_reserve.entities.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {

}
