package com.tingeso.ms_reserve.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String rut;
    private String name;
    private String email;
    private int fidelity;
    private Date birthdate;
    private Double finalFee;
}
