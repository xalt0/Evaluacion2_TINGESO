package com.tingeso.ms_reserve.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private Long id;
    private String name;
    private int loops;
    private int trackTime;
    private int totalTime;
    private int fee;
}
