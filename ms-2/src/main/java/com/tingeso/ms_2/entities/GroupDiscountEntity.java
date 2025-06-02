package com.tingeso.ms_2.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int minUsers;
    private int maxUsers;
    private int discountPercentage;
}