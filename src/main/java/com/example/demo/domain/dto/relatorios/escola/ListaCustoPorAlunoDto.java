package com.example.demo.domain.dto.relatorios.escola;

import java.math.BigDecimal;

public record ListaCustoPorAlunoDto(
        Long escolaId,
        String nomeEscola,
        BigDecimal totalValor,
        Integer numeroAlunos,
        BigDecimal custoAluno
) {
}
