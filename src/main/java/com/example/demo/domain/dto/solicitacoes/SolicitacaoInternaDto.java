package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;

import java.time.LocalDateTime;

public record SolicitacaoInternaDto(
        Long id,
        String nomeUsuario,
        TipoSolicitacao tipoSolicitacao,
        StatusSolicitacao statusSolicitacao,
        LocalDateTime criadoEm
) {
}
