package com.example.demo.domain.dto.combos.item;

import jakarta.validation.constraints.NotBlank;

public record CriarItemDto(
        @NotBlank
        String nome
) {
}
