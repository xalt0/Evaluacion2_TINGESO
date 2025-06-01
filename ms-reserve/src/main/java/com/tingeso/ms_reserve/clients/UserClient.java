package com.tingeso.ms_reserve.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-user")
public interface UserClient {

    @PutMapping("/users/{id}/add-fidelity")
    void addFidelityPoint(@PathVariable("id") Long userId);
}