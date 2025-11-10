package com.example.demo.domain.dto.relatorios;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HeaderPainelSetorDto(
        LocalDate competenciaAtual,
        Integer quantidadeSetores,
        Integer quantidadeEscolas,
        Integer quantidadeAlunos,
        BigDecimal valorTotalCompetencia
) {
}
