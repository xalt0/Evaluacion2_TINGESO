package com.tingeso.ms_3.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fidelity_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FidelityDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int minPoints;
    private int discountPercentage;
}