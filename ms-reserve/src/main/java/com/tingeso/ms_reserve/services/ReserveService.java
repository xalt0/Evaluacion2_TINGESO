package com.tingeso.ms_reserve.services;

import com.tingeso.ms_reserve.DTOs.ReserveRackDTO;
import com.tingeso.ms_reserve.clients.*;
import com.tingeso.ms_reserve.entities.ReserveEntity;
import com.tingeso.ms_reserve.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import feign.FeignException;

@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private KartClient kartClient;

    @Autowired
    private GroupDiscountClient groupDiscountClient;

    @Autowired
    private FidelityDiscountClient fidelityDiscountClient;

    private static final Logger logger = LoggerFactory.getLogger(ReserveService.class);

    // Listar reservas
    public List<ReserveEntity> getAllReserves() {
        return reserveRepository.findAll();
    }

    // Obtener una reserva por ID
    public Optional<ReserveEntity> getReserveById(Long id) {
        return reserveRepository.findById(id);
    }

    // Guardar una nueva reserva
    public ReserveEntity saveReserve(ReserveEntity reserve) {
        logger.info("Guardando nueva reserva con usuarios: {}", reserve.getUserIds());

        // Calcular endTime si corresponde
        if (reserve.getStartTime() != null && reserve.getTotalTime() > 0) {
            reserve.setEndTime(reserve.getStartTime().plusMinutes(reserve.getTotalTime()));
            logger.debug("EndTime calculado: {}", reserve.getEndTime());
        }

        // Obtener descuento grupal
        int groupSize = reserve.getUserIds().size();
        int groupDiscount = 0;
        try {
            groupDiscount = groupDiscountClient.getBestDiscount(groupSize);
            logger.info("Descuento grupal para {} personas: {}%", groupSize, groupDiscount);
        } catch (Exception e) {
            logger.warn("No se pudo obtener descuento grupal. Usando 0%. Detalle: {}", e.getMessage());
        }

        Map<Long, Double> userFees = new HashMap<>();

        for (Long userId : reserve.getUserIds()) {
            int fidelityPoints = 0;
            try {
                fidelityPoints = userClient.getUserById(userId).getFidelity();
                logger.info("Usuario {} tiene {} puntos de fidelidad", userId, fidelityPoints);
            } catch (Exception e) {
                logger.error("Usuario no encontrado: {}", userId);
                throw new IllegalArgumentException("Usuario no encontrado: " + userId);
            }

            int fidelityDiscount = 0;
            try {
                fidelityDiscount = fidelityDiscountClient.getBestDiscount(fidelityPoints);
                logger.info("Descuento de fidelidad para usuario {}: {}%", userId, fidelityDiscount);
            } catch (Exception e) {
                logger.warn("No se pudo obtener descuento de fidelidad para usuario {}. Detalle: {}", userId, e.getMessage());
            }

            // Elegir el mejor descuento
            int bestDiscount = Math.max(groupDiscount, fidelityDiscount);
            double discountedFee = reserve.getFee() * (1 - bestDiscount / 100.0);
            userFees.put(userId, discountedFee);

            logger.info("Descuento aplicado para usuario {}: {}%. Fee final: {}", userId, bestDiscount, discountedFee);

            // Aumentar fidelidad
            try {
                userClient.addFidelityPoint(userId);
                logger.debug("Fidelidad aumentada para usuario {}", userId);
            } catch (Exception e) {
                logger.warn("No se pudo aumentar la fidelidad para usuario {}. Detalle: {}", userId, e.getMessage());
            }
        }

        reserve.setUserFees(userFees);
        logger.info("Reserva completada. Total usuarios: {}. Reserva lista para guardar.", userFees.size());

        return reserveRepository.save(reserve);
    }

    // Actualizar una reserva
    public ReserveEntity updateReserve(ReserveEntity reserve) {
        if (reserve.getStartTime() != null && reserve.getTotalTime() > 0) {
            reserve.setEndTime(reserve.getStartTime().plusMinutes(reserve.getTotalTime()));
        }
        return reserveRepository.save(reserve);
    }

    // Eliminar una reserva
    public boolean deleteReserve(Long id) {
        Optional<ReserveEntity> reserve = reserveRepository.findById(id);
        if (reserve.isPresent()) {
            reserveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar existencia de Usuarios y Karts.
    public void validateExistence(List<Long> userIds, List<Long> kartIds) {
        for (Long userId : userIds) {
            try {
                userClient.getUserById(userId);
            } catch (FeignException.NotFound e) {
                throw new IllegalArgumentException("User ID no existe: " + userId);
            }
        }
        for (Long kartId : kartIds) {
            try {
                kartClient.getKartById(kartId);
            } catch (FeignException.NotFound e) {
                throw new IllegalArgumentException("Kart ID no existe: " + kartId);
            }
        }
    }

    // Retorna información para generar rack.
    public List<ReserveRackDTO> getReservesForRack(LocalDate startDate, LocalDate endDate) {
        List<ReserveEntity> reserves = reserveRepository.findByScheduleDateBetween(startDate, endDate);

        return reserves.stream()
                .map(r -> new ReserveRackDTO(r.getId(), r.getScheduleDate(), r.getStartTime(), r.getEndTime()))
                .collect(Collectors.toList());
    }
}
