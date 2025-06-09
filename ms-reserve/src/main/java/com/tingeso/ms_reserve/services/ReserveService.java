package com.tingeso.ms_reserve.services;

import com.tingeso.ms_reserve.DTOs.*;
import com.tingeso.ms_reserve.DTOs.ReserveRackDTO;
import com.tingeso.ms_reserve.clients.*;
import com.tingeso.ms_reserve.entities.ReserveEntity;
import com.tingeso.ms_reserve.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private DateDiscountClient dateDiscountClient;

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

        // Validar que la lista de usuarios no sea null ni vacía
        if (reserve.getUserIds() == null || reserve.getUserIds().isEmpty()) {
            throw new IllegalArgumentException("La reserva debe contener al menos un usuario.");
        }

        // Calcular endTime
        if (reserve.getStartTime() != null && reserve.getTotalTime() > 0) {
            reserve.setEndTime(reserve.getStartTime().plusMinutes(reserve.getTotalTime()));
            logger.debug("EndTime calculado: {}", reserve.getEndTime());
        }

        int groupSize = reserve.getUserIds().size();
        int groupDiscount = 0;
        try {
            groupDiscount = groupDiscountClient.getBestDiscount(groupSize);
            logger.info("Descuento grupal para {} personas: {}%", groupSize, groupDiscount);
        } catch (Exception e) {
            logger.warn("No se pudo obtener descuento grupal. Detalle: {}", e.getMessage());
        }

        // Obtener usuarios
        List<UserDTO> users = new ArrayList<>();
        for (Long userId : reserve.getUserIds()) {
            try {
                users.add(userClient.getUserById(userId));
            } catch (Exception e) {
                logger.error("Usuario no encontrado: {}", userId);
                throw new IllegalArgumentException("Usuario no encontrado: " + userId);
            }
        }

        // Cumpleaños elegibles
        List<Long> birthdayUserIds = getEligibleBirthdayUserIds(users, reserve.getScheduleDate());

        Map<Long, Double> userFees = new HashMap<>();

        for (UserDTO user : users) {
            int fidelityPoints = user.getFidelity();
            int fidelityDiscount = 0;
            try {
                fidelityDiscount = fidelityDiscountClient.getBestDiscount(fidelityPoints);
                logger.info("Descuento fidelidad usuario {}: {}%", user.getId(), fidelityDiscount);
            } catch (Exception e) {
                logger.warn("No se pudo obtener descuento fidelidad para usuario {}. Detalle: {}", user.getId(), e.getMessage());
            }

            // Preparar request para date-discount-service
            LocalDate birthday = null;
            if (birthdayUserIds.contains(user.getId())) {
                birthday = Instant.ofEpochMilli(user.getBirthdate().getTime())
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate();
            }

            double dateDiscount = 0.0;
            try {
                DiscountDTO dto = new DiscountDTO();
                dto.setUserId(user.getId());
                dto.setScheduleDate(reserve.getScheduleDate());
                dto.setBirthdate(birthday);

                dateDiscount = dateDiscountClient.getBestDateDiscount(dto);
                logger.info("Descuento por fecha para usuario {}: {}%", user.getId(), dateDiscount * 100);
            } catch (Exception e) {
                logger.warn("No se pudo obtener descuento por fecha para usuario {}. Detalle: {}", user.getId(), e.getMessage());
            }

            // Calcular mejor descuento
            double bestDiscount = Stream.of(
                    fidelityDiscount / 100.0,
                    groupDiscount / 100.0,
                    dateDiscount
            ).max(Double::compare).orElse(0.0);

            double finalFee = reserve.getFee() * (1 - bestDiscount);
            userFees.put(user.getId(), finalFee);
            logger.info("Usuario {}: mejor descuento aplicado: {}%. Tarifa final: {}", user.getId(), bestDiscount * 100, finalFee);

            try {
                userClient.addFidelityPoint(user.getId());
            } catch (Exception e) {
                logger.warn("No se pudo aumentar fidelidad usuario {}. Detalle: {}", user.getId(), e.getMessage());
            }
        }

        reserve.setUserFees(userFees);
        return reserveRepository.save(reserve);
    }


    // Filtro de usuarios aplicables al descuento por cumpleaños
    private List<Long> getEligibleBirthdayUserIds(List<UserDTO> users, LocalDate scheduleDate) {
        int size = users.size();
        int max = 0;

        if (size >= 3 && size <= 5) max = 1;
        else if (size >= 6 && size <= 10) max = 2;

        MonthDay target = MonthDay.from(scheduleDate);

        return users.stream()
                .filter(u -> u.getBirthdate() != null)
                .filter(u -> {
                    try {
                        Instant instant = u.getBirthdate().toInstant();
                        LocalDate localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate(); // CORREGIDO
                        MonthDay userBirth = MonthDay.from(localDate);
                        return userBirth.equals(target);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .limit(max)
                .map(UserDTO::getId)
                .collect(Collectors.toList());
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

    // Retorna información con usuarios y karts para endpoint list.
    public List<ReserveResponseDTO> getAllReserveResponses() {
        List<ReserveEntity> reserves = reserveRepository.findAll();

        return reserves.stream().map(reserve -> {
            ReserveResponseDTO dto = new ReserveResponseDTO();
            dto.setId(reserve.getId());
            dto.setLoops(reserve.getLoops());
            dto.setTrackTime(reserve.getTrackTime());
            dto.setTotalTime(reserve.getTotalTime());
            dto.setFee(reserve.getFee());
            dto.setScheduleDate(reserve.getScheduleDate());
            dto.setStartTime(reserve.getStartTime());
            dto.setEndTime(reserve.getEndTime());
            dto.setUserFees(reserve.getUserFees());

            // Obtener usuarios
            List<UserDTO> users = reserve.getUserIds().stream().map(userId -> {
                try {
                    UserDTO user = userClient.getUserById(userId);
                    Double fee = reserve.getUserFees().get(userId);
                    user.setFinalFee(fee); // <-- Asignar el fee individual
                    return user;
                } catch (Exception e) {
                    logger.warn("No se pudo obtener usuario {}: {}", userId, e.getMessage());
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            dto.setUsers(users);

            // Obtener karts
            List<KartDTO> karts = reserve.getKartIds().stream().map(kartId -> {
                try {
                    return kartClient.getKartById(kartId);
                } catch (Exception e) {
                    logger.warn("No se pudo obtener kart {}: {}", kartId, e.getMessage());
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
            dto.setKarts(karts);

            return dto;
        }).collect(Collectors.toList());
    }

}
