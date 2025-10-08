package com.example.demo.domain.services.usuario.strategy;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;

public interface CriarUsuarioSolicitacaoStrategy {

    boolean possuiEstrutura(Estrutura estrutura);

    void criarUsuario(SolicitacaoCadastroUsuario solicitacao);
}
