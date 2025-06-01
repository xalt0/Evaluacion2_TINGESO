package com.tingeso.ms_reserve.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KartDTO {
    private Long id;
    private String code;
    private String model;
    private Date maintenance;
    private boolean available;
}
