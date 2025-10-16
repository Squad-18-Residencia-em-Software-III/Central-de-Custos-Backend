package com.example.demo.domain.dto.folhaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FolhaPagamentoDto(
        UUID id,
        BigDecimal valor,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
