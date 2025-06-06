package com.tingeso.ms_reserve.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiscountDTO {
    private LocalDate scheduleDate;
    private LocalDate birthdate;
    private Long userId;
}
