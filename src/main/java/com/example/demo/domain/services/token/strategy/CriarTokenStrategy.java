package com.example.demo.domain.services.token.strategy;

import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;

public interface CriarTokenStrategy {
    String geraToken(Usuario usuario);

    TipoToken getTipoToken();
}
