package com.example.demo.domain.dto.combos.item;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemDto(
        UUID id,
        String nome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
