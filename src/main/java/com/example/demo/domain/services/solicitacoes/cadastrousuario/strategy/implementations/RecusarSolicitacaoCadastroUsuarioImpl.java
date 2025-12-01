package com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RecusarSolicitacaoCadastroUsuarioImpl extends AceitarSolicitacaoCadastroStrategy {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository;

    protected RecusarSolicitacaoCadastroUsuarioImpl(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository, SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository1) {
        super(solicitacaoCadastroUsuarioRepository);
        this.solicitacaoCadastroUsuarioRepository = solicitacaoCadastroUsuarioRepository1;
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
