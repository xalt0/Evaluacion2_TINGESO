package com.tingeso.ms_6.clients;

import com.tingeso.ms_6.DTOs.ReserveRackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "ms-reserve")
public interface ReserveClient {

    @GetMapping("/reserves/rack")
    List<ReserveRackDTO> getReservesForRack(
            @RequestParam String startDate,
            @RequestParam String endDate
    );
}