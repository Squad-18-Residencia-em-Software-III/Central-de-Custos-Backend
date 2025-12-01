package com.example.demo.domain.dto.usuario;

import com.example.demo.domain.enums.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioInfoDto (
        UUID id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Genero genero,
        LocalDate dataNascimento,
        UUID estruturaId,
        String estruturaNome,
        String logradouro,
        String numeroRua,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        boolean primeiroAcesso,
        String perfilNome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) { }
