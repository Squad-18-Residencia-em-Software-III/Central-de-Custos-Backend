package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.entities.usuario.Genero;

import java.util.UUID;

public record CadastroUsuarioDto(
        UUID id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Genero genero,
        UUID estruturaId,
        String logradouro,
        Integer numeroRua,
        String complemento,
        String bairro,
        Long municipioId,
        Integer cep
) {
}
