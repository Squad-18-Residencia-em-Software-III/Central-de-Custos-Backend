package com.example.demo.domain.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CpfDto(
        @NotBlank
        @CPF
        String cpf
) {
}
