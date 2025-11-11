package com.example.demo.domain.dto.combos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriarComboDto(
        @NotBlank
        String nome,
        @NotNull
        UUID competenciaId,
        List<UUID> itens,
        List<UUID> estruturas
) {
}
