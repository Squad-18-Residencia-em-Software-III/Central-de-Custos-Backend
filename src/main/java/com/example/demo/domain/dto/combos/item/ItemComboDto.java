package com.example.demo.domain.dto.combos.item;

import com.example.demo.domain.enums.UnidadeMedida;

import java.math.BigDecimal;
import java.util.UUID;


public record ItemComboDto(
        UUID uuid,
        String nome,
        BigDecimal valor,
        UUID valorUuid,
        UnidadeMedida unidadeMedida,
        BigDecimal quantidadeUnidadeMedida
) {

}

