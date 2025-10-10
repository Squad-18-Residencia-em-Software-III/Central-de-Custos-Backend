package com.example.demo.domain.dto.combos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record CriarComboDto(
        @NotBlank
        String nome,
        List<UUID> itens,
        List<UUID> estruturas
) {
}
