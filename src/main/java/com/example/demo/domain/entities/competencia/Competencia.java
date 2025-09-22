package com.example.demo.domain.entities.competencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "competencia")
@Table(name = "competencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate referencia;

    @Enumerated(EnumType.STRING)
    private StatusCompetencia status;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column(nullable = false)
    private LocalDate dataFechamento;
}
