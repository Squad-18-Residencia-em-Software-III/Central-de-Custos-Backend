package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;

public abstract class AceitarSolicitacaoInternaStrategy {

    protected final SolicitacaoInternaRepository solicitacaoInternaRepository;

    protected AceitarSolicitacaoInternaStrategy(SolicitacaoInternaRepository solicitacaoInternaRepository) {
        this.solicitacaoInternaRepository = solicitacaoInternaRepository;
    }

    public abstract void aceitarSolicitacao(SolicitacaoInterna solicitacaoInterna);

    public abstract StatusSolicitacao getStatus();
}
