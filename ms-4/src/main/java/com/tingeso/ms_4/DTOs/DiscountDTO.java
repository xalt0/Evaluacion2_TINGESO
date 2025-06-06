package com.tingeso.ms_4.DTOs;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DiscountDTO {
    private LocalDate scheduleDate;
    private LocalDate birthdate;
    private Long userId;
}
