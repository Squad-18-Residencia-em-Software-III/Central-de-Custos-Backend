package com.example.demo.domain.dto.relatorios.graficos;

import java.math.BigDecimal;

public record GastosTotaisCompetenciaDto(
        String competencia,
        BigDecimal totalValor
) {
}
