package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.competencia.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    Optional<Competencia> findByUuid(UUID competenciaId);

    Optional<Competencia> findByCompetencia(LocalDate hoje);
}
