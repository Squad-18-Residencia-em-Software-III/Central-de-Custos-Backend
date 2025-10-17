package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.implementations;

import com.example.demo.domain.dto.solicitacoes.RespostaSolicitacaoInterna;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.FolhaPagamentoRepository;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.AceitarSolicitacaoInternaStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class AceitarSolicitacaoFolhaPagamentoImpl extends AceitarSolicitacaoInternaStrategy {

    private final FolhaPagamentoRepository folhaPagamentoRepository;

    protected AceitarSolicitacaoFolhaPagamentoImpl(SolicitacaoInternaRepository solicitacaoInternaRepository, FolhaPagamentoRepository folhaPagamentoRepository) {
        super(solicitacaoInternaRepository);
        this.folhaPagamentoRepository = folhaPagamentoRepository;
    }

    @Override
    @Transactional
    public void aceitarSolicitacao(SolicitacaoInterna solicitacaoInterna, RespostaSolicitacaoInterna respostaSolicitacaoInterna) {
        FolhaPagamento folhaPagamento;
        if (solicitacaoInterna.getFolhaPagamento() != null){
            folhaPagamento = solicitacaoInterna.getFolhaPagamento();
            folhaPagamento.setValor(solicitacaoInterna.getValor());
        } else {
            folhaPagamento = new FolhaPagamento();
            folhaPagamento.setValor(solicitacaoInterna.getValor());
            folhaPagamento.setCompetencia(solicitacaoInterna.getCompetencia());
            folhaPagamento.setEstrutura(solicitacaoInterna.getEstrutura());
        }

        folhaPagamentoRepository.save(folhaPagamento);
        solicitacaoInterna.setStatus(StatusSolicitacao.APROVADA);
        if (respostaSolicitacaoInterna != null){
            solicitacaoInterna.setResposta(respostaSolicitacaoInterna.resposta());
        }
        solicitacaoInternaRepository.save(solicitacaoInterna);
    }

    @Override
    public TipoSolicitacao getTipo() {
        return TipoSolicitacao.ALTERAR_VALOR_FOLHA_PAGAMENTO;
    }
}
