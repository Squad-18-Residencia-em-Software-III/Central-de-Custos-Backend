package com.example.demo.domain.dto.combos;

import java.time.LocalDate;
import java.util.UUID;

public record ComboDto(
        UUID id,
        String nome,
        String estruturaNome,
        String competencia
) {
}
