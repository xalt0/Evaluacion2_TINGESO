package com.tingeso.ms_kart.repositories;

import com.tingeso.ms_kart.entities.KartEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<KartEntity, Long> {
    List<KartEntity> findByAvailableTrue();
}