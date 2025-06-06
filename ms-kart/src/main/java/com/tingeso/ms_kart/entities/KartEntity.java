package com.tingeso.ms_kart.entities;

// Librería Lombok (Java): Sirve para reducir el código repetitivo (boilerplate) en clases.
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Jakarta Persistence API (Java): Sirve para mapear clases Java a tablas de bases de datos.
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "karts")
@Data // Métodos útiles como Getters y Setters.
@NoArgsConstructor // Constructor sin argumentos.
@AllArgsConstructor // Constructor con todos los argumentos.

public class KartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // unique = Unicidad de elementos en tabla.
    // nullable = Permiso de valor null.
    @Column(unique = true, nullable = false)
    private Long id;

    private String code; // Código. E.g.: K001
    private String model; // Modelo. E.g.: Sodikart RT8
    private LocalDate maintenance; // Fecha de próxima mantención. E.g.: 2025-10-24 (YYYY-MM-DD en PSQL)
    private boolean available; // Disponibilidad. E.g.: true
}