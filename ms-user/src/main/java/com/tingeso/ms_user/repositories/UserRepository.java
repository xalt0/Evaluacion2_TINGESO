package com.tingeso.ms_user.repositories;

import com.tingeso.ms_user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByRut(String rut);
}
