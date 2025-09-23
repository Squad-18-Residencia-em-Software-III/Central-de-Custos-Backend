package com.example.demo.domain.dto.security;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record LoginDto(
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        String senha
) {
}
