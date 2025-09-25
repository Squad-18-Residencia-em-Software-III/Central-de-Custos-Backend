package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitacoesMapper {

    @Mapping(target = "estrutura", ignore = true)
    @Mapping(target = "municipio", ignore = true)
    SolicitacaoCadastroUsuario cadastroUsuarioToEntity(CadastroUsuarioDto dto);

}
