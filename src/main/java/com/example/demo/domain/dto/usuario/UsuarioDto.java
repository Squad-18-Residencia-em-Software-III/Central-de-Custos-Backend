package com.example.demo.domain.dto.usuario;

import java.util.UUID;

public record UsuarioDto(
        UUID id,
        String nome,
        String estruturaNome,
        String perfilNome,
        boolean primeiroAcesso
) {
}
