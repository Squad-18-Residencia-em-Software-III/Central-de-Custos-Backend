package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.solicitacoes.InfoSolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.SolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.cadastrousuario.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.cadastrousuario.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitacoesMapper {

    @Mapping(target = "estrutura", ignore = true)
    SolicitacaoCadastroUsuario cadastroUsuarioToEntity(SolicitaCadastroUsuarioDto dto);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "estruturaId", source = "estrutura.uuid")
    CadastroUsuarioDto cadastroUsuarioToDto(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "estruturaUsuario", source = "usuario.estrutura.nome")
    @Mapping(target = "estruturaSolicitacao", source = "estrutura.nome")
    @Mapping(target = "competencia", source = "competencia.competencia")
    @Mapping(target = "combo", source = "combo.nome")
    @Mapping(target = "itemCombo", source = "itemCombo.nome")
    @Mapping(target = "novoValor", source = "valor")
    @Mapping(target = "valorItemCombo", source = "valorItemCombo.valor")
    @Mapping(target = "valorFolhaPagamento", source = "folhaPagamento.valor")
    @Mapping(target = "statusSolicitacao", source = "status")
    InfoSolicitacaoInternaDto solicitacaoInternaToInfoDto(SolicitacaoInterna solicitacaoInterna);

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "statusSolicitacao", source = "status")
    SolicitacaoInternaDto solicitacaoInternaToDto(SolicitacaoInterna solicitacaoInterna);

}
