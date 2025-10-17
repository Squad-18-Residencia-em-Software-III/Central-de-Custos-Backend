package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.implementations;

import com.example.demo.domain.dto.solicitacoes.RespostaSolicitacaoInterna;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.AceitarSolicitacaoInternaStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AceitarSolicitacaoValorItemImpl extends AceitarSolicitacaoInternaStrategy {

    private final ValorItemComboRepository valorItemComboRepository;

    protected AceitarSolicitacaoValorItemImpl(SolicitacaoInternaRepository solicitacaoInternaRepository, ValorItemComboRepository valorItemComboRepository) {
        super(solicitacaoInternaRepository);
        this.valorItemComboRepository = valorItemComboRepository;
    }

    @Override
    @Transactional
    public void aceitarSolicitacao(SolicitacaoInterna solicitacaoInterna, RespostaSolicitacaoInterna respostaSolicitacaoInterna) {
        ValorItemCombo valorItemCombo;
        if (solicitacaoInterna.getValorItemCombo() != null){
            valorItemCombo = solicitacaoInterna.getValorItemCombo();
            valorItemCombo.setValor(solicitacaoInterna.getValor());
        } else {
            valorItemCombo = new ValorItemCombo();
            valorItemCombo.setEstrutura(solicitacaoInterna.getEstrutura());
            valorItemCombo.setCombo(solicitacaoInterna.getCombo());
            valorItemCombo.setCompetencia(solicitacaoInterna.getCompetencia());
            valorItemCombo.setItemCombo(solicitacaoInterna.getItemCombo());
            valorItemCombo.setValor(solicitacaoInterna.getValor());
        }

        valorItemComboRepository.save(valorItemCombo);
        solicitacaoInterna.setStatus(StatusSolicitacao.APROVADA);
        if (respostaSolicitacaoInterna != null){
            solicitacaoInterna.setResposta(respostaSolicitacaoInterna.resposta());
        }
        solicitacaoInternaRepository.save(solicitacaoInterna);
    }

    @Override
    public TipoSolicitacao getTipo() {
        return TipoSolicitacao.ALTERAR_VALOR_ITEM_COMBO;
    }
}
