package com.tingeso.ms_4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "date_discounts")
public class DateDiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate scheduleDate;
    private Long userId;
    private Double discountApplied;
    private String discountType;
}
