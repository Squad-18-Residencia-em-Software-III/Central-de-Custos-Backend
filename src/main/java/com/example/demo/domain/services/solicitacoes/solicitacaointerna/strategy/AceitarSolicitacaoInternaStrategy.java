package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy;

import com.example.demo.domain.dto.solicitacoes.RespostaSolicitacaoInterna;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;

public abstract class AceitarSolicitacaoInternaStrategy {

    protected final SolicitacaoInternaRepository solicitacaoInternaRepository;

    protected AceitarSolicitacaoInternaStrategy(SolicitacaoInternaRepository solicitacaoInternaRepository) {
        this.solicitacaoInternaRepository = solicitacaoInternaRepository;
    }

    public abstract void aceitarSolicitacao(SolicitacaoInterna solicitacaoInterna, RespostaSolicitacaoInterna respostaSolicitacaoInterna);

    public abstract TipoSolicitacao getTipo();

}
