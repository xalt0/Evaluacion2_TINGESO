package com.tingeso.ms_reserve.clients;

import com.tingeso.ms_reserve.DTOs.DiscountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "ms-4")
public interface DateDiscountClient {

    @PostMapping("/date-discounts/evaluate")
    Double evaluateDateDiscount(@RequestBody DiscountDTO dto);

    @GetMapping("/best-discount")
    double getBestDateDiscount(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "birthdate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate
    );
}
