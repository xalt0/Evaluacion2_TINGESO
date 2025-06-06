package com.tingeso.ms_4.controllers;

import com.tingeso.ms_4.DTOs.DiscountDTO;
import com.tingeso.ms_4.entities.DateDiscountEntity;
import com.tingeso.ms_4.services.DateDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/date-discounts")
public class DateDiscountController {

    @Autowired
    private DateDiscountService dateDiscountService;

    @PostMapping("/evaluate")
    public double evaluateDiscount(@RequestBody DiscountDTO dto) {
        return dateDiscountService.calculateBestDateDiscount(dto);
    }

    @GetMapping("/by-date")
    public List<DateDiscountEntity> getDiscountsByDate(@RequestParam LocalDate date) {
        return dateDiscountService.getDiscountsByDate(date);
    }
}
