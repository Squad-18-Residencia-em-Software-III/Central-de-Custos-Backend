package com.example.demo.domain.dto.combos.item;

import com.example.demo.domain.enums.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarItemDto(
        @NotBlank
        String nome,
        @NotNull
        UnidadeMedida unidadeMedida
) {
}
