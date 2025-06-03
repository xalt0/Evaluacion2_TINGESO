package com.tingeso.ms_6.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveRackDTO {
    private Long id;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
}