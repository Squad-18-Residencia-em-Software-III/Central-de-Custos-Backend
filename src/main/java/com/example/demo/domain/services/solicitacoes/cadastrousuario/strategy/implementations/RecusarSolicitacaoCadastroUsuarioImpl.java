package com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class RecusarSolicitacaoCadastroUsuarioImpl implements AceitarSolicitacaoCadastroStrategy {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository;

    public RecusarSolicitacaoCadastroUsuarioImpl(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository) {
        this.solicitacaoCadastroUsuarioRepository = solicitacaoCadastroUsuarioRepository;
    }

    @Override
    @Transactional
    public void realiza(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario) {
        solicitacaoCadastroUsuario.setStatus(StatusSolicitacao.RECUSADA);
        solicitacaoCadastroUsuarioRepository.save(solicitacaoCadastroUsuario);
    }

    @Override
    public StatusSolicitacao getStatus() {
        return StatusSolicitacao.RECUSADA;
    }

}
