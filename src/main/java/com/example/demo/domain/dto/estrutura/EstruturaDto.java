package com.example.demo.domain.dto.estrutura;

import com.example.demo.domain.enums.ClassificacaoEstrutura;

import java.util.UUID;

public record EstruturaDto(
        UUID id,
        String nome,
        String municipio,
        String uf,
        ClassificacaoEstrutura classificacaoEstrutura
) {
}
