package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record SolicitaCadastroUsuarioDto(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @CPF
        String cpf,
        Genero genero,
        @NotNull
        LocalDate dataNascimento,
        @NotNull
        UUID estruturaId,
        @NotBlank
        String logradouro,
        Integer numeroRua,
        String complemento,
        @NotBlank
        String bairro,
        @NotBlank
        String municipio,
        @NotBlank
        String estado,
        @NotNull
        Integer cep
) {
}

