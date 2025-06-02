package com.tingeso.ms_reserve.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Long> userIds; // Usuarios registrados en reserva.

    @ElementCollection
    @CollectionTable(name = "reserve_kart_ids", joinColumns = @JoinColumn(name = "reserve_id"))
    @Column(name = "kart_id")
    private List<Long> kartIds; // Karts asignados por reserva.

    private int loops; // Cantidad de vueltas.
    private int trackTime; // Tiempo en pista.
    private int totalTime; // Tiempo total.
    private int fee; // Tarifa total (sin descuento).
    private LocalDate scheduleDate; // Fecha de la reserva.
    private LocalTime startTime; // Inicio.
    private LocalTime endTime; // Fin.

    @ElementCollection
    @CollectionTable(name = "reserve_user_fees", joinColumns = @JoinColumn(name = "reserve_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "discounted_fee")
    private Map<Long, Double> userFees = new HashMap<>(); // Precios a pagar por usuarios en reserva.
}