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
    @PostMapping("/date-discounts/check")
    double getBestDateDiscount(@RequestBody DiscountDTO dto);
}

