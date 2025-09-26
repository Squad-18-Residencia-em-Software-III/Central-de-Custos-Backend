package com.example.demo.domain.mapper;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "senha", ignore = true)
    Usuario toEntity(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

}
