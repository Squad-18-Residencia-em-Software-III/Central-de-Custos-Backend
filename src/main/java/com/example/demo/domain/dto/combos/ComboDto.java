package com.example.demo.domain.dto.combos;

import java.util.UUID;

public record ComboDto(
        UUID id,
        String nome,
        String competencia
) {
}
