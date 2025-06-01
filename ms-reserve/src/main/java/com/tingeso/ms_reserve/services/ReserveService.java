package com.tingeso.ms_reserve.services;

import com.tingeso.ms_reserve.clients.UserClient;
import com.tingeso.ms_reserve.clients.KartClient;
import com.tingeso.ms_reserve.entities.ReserveEntity;
import com.tingeso.ms_reserve.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        // Validar existencia antes de continuar
        validateExistence(reserve.getUserIds(), reserve.getKartIds());
        // Calcular endTime si corresponde
        if (reserve.getStartTime() != null && reserve.getTotalTime() > 0) {
            reserve.setEndTime(reserve.getStartTime().plusMinutes(reserve.getTotalTime()));
        }
        // Agregar puntos de fidelidad
        if (reserve.getUserIds() != null) {
            for (Long userId : reserve.getUserIds()) {
                userClient.addFidelityPoint(userId);
            }
        }
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
                userClient.getUserById(userId); // si no existe, puede lanzar FeignException 404
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
