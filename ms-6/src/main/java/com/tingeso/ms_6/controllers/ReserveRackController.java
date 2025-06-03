package com.tingeso.ms_6.controllers;

import com.tingeso.ms_6.DTOs.ReserveRackDTO;
import com.tingeso.ms_6.services.ReserveRackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rack")
public class ReserveRackController {

    @Autowired
    private ReserveRackService rackService;

    @GetMapping
    public List<ReserveRackDTO> getWeeklyRack(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rackService.getWeeklyRack(startDate, endDate);
    }
}