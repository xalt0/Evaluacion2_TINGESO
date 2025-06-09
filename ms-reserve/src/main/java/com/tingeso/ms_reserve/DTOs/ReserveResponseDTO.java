package com.tingeso.ms_reserve.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveResponseDTO {
    private Long id;
    private List<UserDTO> users;
    private List<KartDTO> karts;
    private int loops;
    private int trackTime;
    private int totalTime;
    private int fee;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Map<Long, Double> userFees;
}

