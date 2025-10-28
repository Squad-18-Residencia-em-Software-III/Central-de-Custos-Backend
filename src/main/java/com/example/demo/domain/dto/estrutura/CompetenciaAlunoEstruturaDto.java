package com.example.demo.domain.dto.estrutura;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CompetenciaAlunoEstruturaDto(
        @NotNull
        UUID estruturaId,
        @NotNull
        UUID competenciaId,
        @NotNull
        Integer numeroAlunos
) {
}
