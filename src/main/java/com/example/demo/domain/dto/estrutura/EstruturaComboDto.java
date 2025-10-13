package com.example.demo.domain.dto.estrutura;

import com.example.demo.domain.enums.ClassificacaoEstrutura;

import java.util.UUID;

public record EstruturaComboDto(
        UUID id,
        String nome,
        ClassificacaoEstrutura classificacaoEstrutura,
        String municipio,
        String uf
) {
}
