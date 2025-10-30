package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.usuario.UsuarioDto;
import com.example.demo.domain.dto.usuario.UsuarioInfoDto;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "senha", ignore = true)
    Usuario toEntity(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "estruturaId", source = "estrutura.uuid")
    @Mapping(target = "estruturaNome", source = "estrutura.nome")
    @Mapping(target = "perfilNome", source = "perfil.nome")
    UsuarioInfoDto usuarioToInfoDto(Usuario usuario);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "estruturaNome", source = "estrutura.nome")
    @Mapping(target = "perfilNome", source = "perfil.nome")
    UsuarioDto usuarioToDto(Usuario usuario);

}
