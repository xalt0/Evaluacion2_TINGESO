package com.tingeso.ms_reserve.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.tingeso.ms_reserve.DTOs.UserDTO;

import java.util.List;

@FeignClient(name = "ms-user")
public interface UserClient {

    @GetMapping("/users/list")
    List<UserDTO> getAllUsers();

    @PutMapping("/users/{id}/add-fidelity")
    void addFidelityPoint(@PathVariable("id") Long userId);

    @GetMapping("/users/get/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}