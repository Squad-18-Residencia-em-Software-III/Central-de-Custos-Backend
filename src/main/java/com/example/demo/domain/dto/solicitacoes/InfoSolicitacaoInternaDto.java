package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record InfoSolicitacaoInternaDto(
        Long id,
        String nomeUsuario,
        String estruturaUsuario,
        TipoSolicitacao tipoSolicitacao,
        StatusSolicitacao statusSolicitacao,
        String estruturaSolicitacao,
        LocalDate competencia,
        String combo,
        String itemCombo,
        BigDecimal novoValor,
        BigDecimal valorItemCombo,
        BigDecimal valorFolhaPagamento,
        LocalDateTime criadoEm
) {
}
