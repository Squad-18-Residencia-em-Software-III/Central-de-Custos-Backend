package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.Genero;

import java.time.LocalDateTime;
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
        UUID municipioId,
        Integer cep,
        LocalDateTime criadoEm,
        StatusSolicitacao status
) {
}
