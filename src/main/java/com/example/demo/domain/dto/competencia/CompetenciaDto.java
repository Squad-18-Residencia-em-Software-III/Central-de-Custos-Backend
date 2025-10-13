package com.example.demo.domain.dto.competencia;

import com.example.demo.domain.enums.StatusCompetencia;

import java.time.LocalDate;
import java.util.UUID;

public record CompetenciaDto(
        UUID id,
        LocalDate competencia,
        StatusCompetencia statusCompetencia
) {
}
