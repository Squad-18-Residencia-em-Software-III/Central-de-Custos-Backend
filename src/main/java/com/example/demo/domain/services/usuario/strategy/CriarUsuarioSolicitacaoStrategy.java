package com.example.demo.domain.services.usuario.strategy;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;

public interface CriarUsuarioSolicitacaoStrategy {

    void criarUsuario(SolicitacaoCadastroUsuario solicitacao);

    String getEstrutura();
}
