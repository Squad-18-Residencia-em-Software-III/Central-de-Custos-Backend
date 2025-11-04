package com.example.demo.domain.dto.relatorios.escola;

import java.math.BigDecimal;

public record CustoPorAlunoDto(
        String competencia,
        BigDecimal totalValor,
        Integer numeroAlunos,
        BigDecimal custoAluno
) {
}
