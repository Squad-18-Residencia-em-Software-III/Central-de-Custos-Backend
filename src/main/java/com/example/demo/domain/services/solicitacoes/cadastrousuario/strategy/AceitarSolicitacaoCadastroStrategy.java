package com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;

public abstract class AceitarSolicitacaoCadastroStrategy {

    protected final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository;

    protected AceitarSolicitacaoCadastroStrategy(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository) {
        this.solicitacaoCadastroUsuarioRepository = solicitacaoCadastroUsuarioRepository;
    }

    public abstract void realiza(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario);

    public abstract StatusSolicitacao getStatus();

}
