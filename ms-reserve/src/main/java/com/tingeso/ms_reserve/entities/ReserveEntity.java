package com.tingeso.ms_reserve.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reserves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "reserve_user_ids", joinColumns = @JoinColumn(name = "reserve_id"))
    @Column(name = "user_id")
    private List<Long> userIds;

    @ElementCollection
    @CollectionTable(name = "reserve_kart_ids", joinColumns = @JoinColumn(name = "reserve_id"))
    @Column(name = "kart_id")
    private List<Long> kartIds;

    private int loops; // Cantidad de vueltas.
    private int trackTime; // Tiempo en pista.
    private int totalTime; // Tiempo total.
    private int fee; // Tarifa total (sin descuento).
    private LocalDate scheduleDate; // Fecha de la reserva.
    private LocalTime startTime; // Inicio.
    private LocalTime endTime; // Fin.
}