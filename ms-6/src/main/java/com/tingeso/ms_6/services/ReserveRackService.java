package com.tingeso.ms_6.services;

import com.tingeso.ms_6.DTOs.ReserveRackDTO;
import com.tingeso.ms_6.clients.ReserveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReserveRackService {

    @Autowired
    private ReserveClient reserveClient;

    public List<ReserveRackDTO> getWeeklyRack(LocalDate startDate, LocalDate endDate) {
        return reserveClient.getReservesForRack(startDate.toString(), endDate.toString());
    }
}
