package com.example.demo.domain.dto.folhaPagamento;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record InserirFolhaPagamentoDto(
        @NotNull
        UUID estruturaId,
        @NotNull
        UUID competenciaId,
        @NotNull
        BigDecimal valor
) {
}
