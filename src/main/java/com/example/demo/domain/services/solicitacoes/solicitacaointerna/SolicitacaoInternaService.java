package com.example.demo.domain.services.solicitacoes.solicitacaointerna;

import com.example.demo.domain.dto.solicitacoes.InfoSolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.RespostaSolicitacaoInterna;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.services.solicitacoes.factory.SolicitacaoFactory;
import com.example.demo.domain.validations.SolicitacaoValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SolicitacaoInternaService {

    private final SolicitacaoFactory solicitacaoFactory;
    private final SolicitacaoValidator solicitacaoValidator;
    private final SolicitacaoInternaRepository solicitacaoInternaRepository;
    private final SolicitacoesMapper solicitacoesMapper;

    public SolicitacaoInternaService(SolicitacaoFactory solicitacaoFactory, SolicitacaoValidator solicitacaoValidator, SolicitacaoInternaRepository solicitacaoInternaRepository, SolicitacoesMapper solicitacoesMapper) {
        this.solicitacaoFactory = solicitacaoFactory;
        this.solicitacaoValidator = solicitacaoValidator;
        this.solicitacaoInternaRepository = solicitacaoInternaRepository;
        this.solicitacoesMapper = solicitacoesMapper;
    }

    @Transactional
    public void novaSolicitacao(NovaSolicitacaoInternaDto dto){
        solicitacaoFactory.solicitacaoInternaTipo(dto.tipoSolicitacao()).realiza(dto);
    }

    @Transactional
    public void definirStatusSolicitacao(Long solicitacaoInternaId, StatusSolicitacao statusSolicitacao, RespostaSolicitacaoInterna resposta){
        SolicitacaoInterna solicitacaoInterna = solicitacaoValidator.validaSolicitacaoInternaExiste(solicitacaoInternaId);
        switch (statusSolicitacao){
            case APROVADA -> solicitacaoFactory.aceitarSolicitacaoInternaTipo(solicitacaoInterna.getTipoSolicitacao()).aceitarSolicitacao(solicitacaoInterna, resposta);

            case RECUSADA -> {
                solicitacaoInterna.setStatus(statusSolicitacao);
                solicitacaoInterna.setResposta(resposta.resposta());
                solicitacaoInternaRepository.save(solicitacaoInterna);
            }

            case PENDENTE -> throw new BusinessException("Tipo inválido");

            default -> throw new BusinessException("Status de solicitação não reconhecido: " + statusSolicitacao);
        }
    }

    public InfoSolicitacaoInternaDto buscarDetalhesSolicitacao(Long id){
        SolicitacaoInterna solicitacaoInterna = solicitacaoValidator.validaSolicitacaoInternaExiste(id);
        return solicitacoesMapper.solicitacaoInternaToInfoDto(solicitacaoInterna);
    }

}
