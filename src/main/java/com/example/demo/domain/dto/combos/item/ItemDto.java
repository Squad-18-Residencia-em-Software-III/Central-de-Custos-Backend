package com.example.demo.domain.dto.combos.item;

import com.example.demo.domain.enums.UnidadeMedida;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemDto(
        UUID id,
        String nome,
        UnidadeMedida unidadeMedida,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
