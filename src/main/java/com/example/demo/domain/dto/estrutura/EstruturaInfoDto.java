package com.example.demo.domain.dto.estrutura;

import com.example.demo.domain.enums.ClassificacaoEstrutura;

import java.util.List;
import java.util.UUID;

public record EstruturaInfoDto(
        UUID id,
        String nome,
        ClassificacaoEstrutura classificacaoEstrutura,
        String municipio,
        String uf,
        String telefone,
        String logradouro,
        String complemento,
        Integer numeroRua,
        String bairro,
        String cep,
        List<EstruturaDto> subSetores
) {
}
