package com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;

public interface AceitarSolicitacaoCadastroStrategy {

    void realiza(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

    boolean statusSolicitacao(StatusSolicitacao solicitacao);

}
