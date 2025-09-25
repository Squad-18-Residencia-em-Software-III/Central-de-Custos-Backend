package com.example.demo.domain.dto.solicitacoes;

import com.example.demo.domain.entities.usuario.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record CadastroUsuarioDto(
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
        @NotBlank
        String senha,
        Genero genero,
        @NotNull
        UUID estruturaId,
        @NotBlank
        String logradouro,
        Integer numeroRua,
        String complemento,
        @NotBlank
        String bairro,
        @NotNull
        Long municipioId,
        @NotNull
        Integer cep
) {
}
