package com.tingeso.ms_reserve.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDiscountDTO {
    private Long id;
    private int minUsers;
    private int maxUsers;
    private int discountPercentage;
}
