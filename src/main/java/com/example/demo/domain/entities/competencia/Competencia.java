package com.example.demo.domain.entities.competencia;

import com.example.demo.domain.enums.StatusCompetencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "competencia")
@Table(name = "competencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column(nullable = false)
    private LocalDate dataFechamento;
}
