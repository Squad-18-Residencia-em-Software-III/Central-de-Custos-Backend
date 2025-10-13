package com.example.demo.domain.services.solicitacoes.factory;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class SolicitacaoFactory {

    private final Map<StatusSolicitacao, AceitarSolicitacaoCadastroStrategy> aceiteSolicitacaoCadastroStrategies = new HashMap<>();

    public SolicitacaoFactory(Set<AceitarSolicitacaoCadastroStrategy> aceiteSolicitacaoCadastroSet){
        aceiteSolicitacaoCadastroSet.forEach(s -> aceiteSolicitacaoCadastroStrategies.put(s.getStatus(), s));
    }

    public AceitarSolicitacaoCadastroStrategy solicitacaoCadastroStatus(StatusSolicitacao statusSolicitacao){
        AceitarSolicitacaoCadastroStrategy strategy = aceiteSolicitacaoCadastroStrategies.get(statusSolicitacao);
        if (strategy == null){
            throw new EntityNotFoundException("Tipo não encontrado ou inválido");
        }
        return strategy;
    }

}
