package com.example.demo.domain.dto.combos.item;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record InserirValorItemDto(
        @NotNull
        UUID itemId,
        @NotNull
        UUID comboId,
        @NotNull
        UUID estruturaId,
        BigDecimal valor,
        BigDecimal quantidadeUnidadeMedida
) {
}
