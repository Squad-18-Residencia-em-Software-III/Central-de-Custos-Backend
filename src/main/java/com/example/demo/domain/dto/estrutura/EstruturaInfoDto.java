package com.example.demo.domain.dto.estrutura;

import com.example.demo.domain.enums.ClassificacaoEstrutura;

public record EstruturaInfoDto(
        String nome,
        ClassificacaoEstrutura classificacaoEstrutura,
        String municipio,
        String uf,
        String telefone,
        String logradouro,
        String complemento,
        Integer numeroRua,
        String bairro,
        String cep
) {
}
