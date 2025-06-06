package com.tingeso.ms_4.controllers;

import com.tingeso.ms_4.DTOs.DiscountDTO;
import com.tingeso.ms_4.entities.DateDiscountEntity;
import com.tingeso.ms_4.services.DateDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/date-discounts")
public class DateDiscountController {

    @Autowired
    private DateDiscountService dateDiscountService;

    // Endpoint que calcula el mejor descuento por fecha
    @PostMapping("/check")
    public ResponseEntity<Double> getBestDateDiscount(@RequestBody DiscountDTO dto) {
        if (dto.getScheduleDate() == null || dto.getUserId() == null) {
            return ResponseEntity.badRequest().body(0.0);
        }

        double bestDiscount = dateDiscountService.calculateBestDateDiscount(dto);
        return ResponseEntity.ok(bestDiscount);
    }

    // Obtener todos los descuentos aplicados en una fecha
    @GetMapping("/by-date")
    public ResponseEntity<List<DateDiscountEntity>> getDiscountsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DateDiscountEntity> discounts = dateDiscountService.getDiscountsByDate(date);
        return ResponseEntity.ok(discounts);
    }
}

