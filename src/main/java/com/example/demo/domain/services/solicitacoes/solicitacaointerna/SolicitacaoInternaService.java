package com.example.demo.domain.services.solicitacoes.solicitacaointerna;

import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.services.solicitacoes.factory.SolicitacaoFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoInternaService {

    private final SolicitacaoFactory solicitacaoFactory;

    public SolicitacaoInternaService(SolicitacaoFactory solicitacaoFactory) {
        this.solicitacaoFactory = solicitacaoFactory;
    }

    @Transactional
    public void novaSolicitacao(NovaSolicitacaoInternaDto dto){
        solicitacaoFactory.solicitacaoInternaTipo(dto.tipoSolicitacao()).realiza(dto);
    }

}
