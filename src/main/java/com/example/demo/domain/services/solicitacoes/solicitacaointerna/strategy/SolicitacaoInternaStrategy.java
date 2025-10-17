package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy;

import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.enums.TipoSolicitacao;

public interface SolicitacaoInternaStrategy {

    void realiza(SolicitaCadastroUsuarioDto dto);

    TipoSolicitacao getTipo();

}
