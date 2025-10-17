package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.solicitacoes.cadastrousuario.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.cadastrousuario.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitacoesMapper {

    @Mapping(target = "estrutura", ignore = true)
    SolicitacaoCadastroUsuario cadastroUsuarioToEntity(SolicitaCadastroUsuarioDto dto);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "estruturaId", source = "estrutura.uuid")
    CadastroUsuarioDto cadastroUsuarioToDto(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

}
