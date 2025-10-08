package com.example.demo.domain.services.usuario.strategy;

import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;

public interface DefinirSenhaUsuarioStrategy {

    void definirSenha(Usuario usuario, NovaSenhaDto senhaDto);

    TipoToken getTipoToken();

}
