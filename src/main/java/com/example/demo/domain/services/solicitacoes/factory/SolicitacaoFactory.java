package com.example.demo.domain.services.solicitacoes.factory;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.SolicitacaoInternaStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class SolicitacaoFactory {

    private final Map<StatusSolicitacao, AceitarSolicitacaoCadastroStrategy> aceiteSolicitacaoCadastroStrategies = new HashMap<>();
    private final Map<TipoSolicitacao, SolicitacaoInternaStrategy> solicitacaoInternaStrategies = new HashMap<>();

    public SolicitacaoFactory(Set<AceitarSolicitacaoCadastroStrategy> aceiteSolicitacaoCadastroSet, Set<SolicitacaoInternaStrategy> solicitacaoInternaSets){
        aceiteSolicitacaoCadastroSet.forEach(s -> aceiteSolicitacaoCadastroStrategies.put(s.getStatus(), s));
        solicitacaoInternaSets.forEach(s -> solicitacaoInternaStrategies.put(s.getTipo(), s));
    }

    public AceitarSolicitacaoCadastroStrategy solicitacaoCadastroStatus(StatusSolicitacao statusSolicitacao){
        AceitarSolicitacaoCadastroStrategy strategy = aceiteSolicitacaoCadastroStrategies.get(statusSolicitacao);
        if (strategy == null){
            throw new EntityNotFoundException("Tipo não encontrado ou inválido");
        }
        return strategy;
    }

    public SolicitacaoInternaStrategy solicitacaoInternaTipo(TipoSolicitacao tipoSolicitacao){
        SolicitacaoInternaStrategy strategy = solicitacaoInternaStrategies.get(tipoSolicitacao);
        if (strategy == null){
            throw new EntityNotFoundException("Tipo não encontrado ou inválido");
        }
        return strategy;
    }

}
