package com.tingeso.ms_reserve.controllers;

import com.tingeso.ms_reserve.DTOs.ReserveRackDTO;
import com.tingeso.ms_reserve.DTOs.ReserveResponseDTO;
import com.tingeso.ms_reserve.entities.ReserveEntity;
import com.tingeso.ms_reserve.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reserves")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    // Listar todas las reservas
    @GetMapping("/list")
    public List<ReserveResponseDTO> getAllReserveResponses() {
        return reserveService.getAllReserveResponses();
    }

    // Obtener reserva por ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ReserveEntity> getReserveById(@PathVariable Long id) {
        return reserveService.getReserveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva reserva
    @PostMapping("/save")
    public ReserveEntity createReserve(@RequestBody ReserveEntity reserve) {
        return reserveService.saveReserve(reserve);
    }

    // Actualizar reserva
    @PutMapping("/update")
    public ReserveEntity updateReserve(@RequestBody ReserveEntity reserve) {
        return reserveService.updateReserve(reserve);
    }

    // Eliminar reserva
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable Long id) {
        try {
            boolean deleted = reserveService.deleteReserve(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/rack")
    public List<ReserveRackDTO> getReservesForRack(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return reserveService.getReservesForRack(start, end);
    }
}
