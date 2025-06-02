package com.tingeso.ms_reserve.clients;

import com.tingeso.ms_reserve.DTOs.PlanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-1", url = "http://ms-1/plans")
public interface PlanClient {

    @GetMapping("/get/{name}")
    PlanDTO getPlanByName(@PathVariable String name);
}
