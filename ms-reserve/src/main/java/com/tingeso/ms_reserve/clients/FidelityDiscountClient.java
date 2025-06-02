package com.tingeso.ms_reserve.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-3")
public interface FidelityDiscountClient {

    @GetMapping("/fidelity-discounts/best/{points}")
    Integer getBestDiscount(@PathVariable("points") int points);
}