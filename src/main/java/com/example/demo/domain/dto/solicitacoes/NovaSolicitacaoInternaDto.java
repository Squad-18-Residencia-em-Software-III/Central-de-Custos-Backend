package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.enums.TipoSolicitacao;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public record NovaSolicitacaoInternaDto(
        @NotBlank
        String descricao,
        BigDecimal valor,
        TipoSolicitacao tipoSolicitacao,
        UUID estruturaId,
        UUID folhaPagamentoId,
        UUID comboId,
        UUID itemComboId,
        UUID valorItemComboId
) {
}
