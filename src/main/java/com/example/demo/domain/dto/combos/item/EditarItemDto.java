package com.example.demo.domain.dto.combos.item;

import com.example.demo.domain.enums.UnidadeMedida;

public record EditarItemDto(
        String nome,
        UnidadeMedida unidadeMedida
) {
}
