package com.tingeso.ms_reserve.services;

import com.tingeso.ms_reserve.clients.*;
import com.tingeso.ms_reserve.entities.ReserveEntity;
import com.tingeso.ms_reserve.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        // Calcular endTime si corresponde
        if (reserve.getStartTime() != null && reserve.getTotalTime() > 0) {
            reserve.setEndTime(reserve.getStartTime().plusMinutes(reserve.getTotalTime()));
        }

        // Obtener descuentos aplicables
        int groupSize = reserve.getUserIds().size();
        int groupDiscount = 0;
        try {
            groupDiscount = groupDiscountClient.getBestDiscount(groupSize);
        } catch (Exception e) {
            // Log o fallback
        }

        // Si quieres guardar el fee individual por usuario
        Map<Long, Double> userFees = new HashMap<>();

        for (Long userId : reserve.getUserIds()) {
            int fidelityPoints = 0;
            try {
                fidelityPoints = userClient.getUserById(userId).getFidelity();
            } catch (Exception e) {
                throw new IllegalArgumentException("Usuario no encontrado: " + userId);
            }

            int fidelityDiscount = 0;
            try {
                fidelityDiscount = fidelityDiscountClient.getBestDiscount(fidelityPoints);
            } catch (Exception e) {
                // Log o fallback
            }

            // Elegir el mejor descuento
            int bestDiscount = Math.max(groupDiscount, fidelityDiscount);
            double discountedFee = reserve.getFee() * (1 - bestDiscount / 100.0);
            userFees.put(userId, discountedFee);

            // Aumentar fidelidad
            try {
                userClient.addFidelityPoint(userId);
            } catch (Exception ignored) {}
        }

        // Guardar los fees individuales si tienes un campo userFees
        reserve.setUserFees(userFees);

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
}
