package com.tingeso.ms_reserve.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.tingeso.ms_reserve.DTOs.KartDTO;

@FeignClient(name = "ms-kart")
public interface KartClient {

    @GetMapping("/karts/get/{id}")
    KartDTO getKartById(@PathVariable("id") Long id);
}