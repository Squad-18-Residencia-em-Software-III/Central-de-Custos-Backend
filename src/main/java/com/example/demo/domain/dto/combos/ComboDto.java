package com.example.demo.domain.dto.combos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComboDto(
        UUID id,
        String nome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
