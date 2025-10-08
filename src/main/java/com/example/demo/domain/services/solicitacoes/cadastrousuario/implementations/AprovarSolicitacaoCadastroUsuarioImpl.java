package com.example.demo.domain.services.solicitacoes.cadastrousuario.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.services.usuario.UsuarioService;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AprovarSolicitacaoCadastroUsuarioImpl implements AceitarSolicitacaoCadastroStrategy {

    private final UsuarioService usuarioService;
    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository;

    public AprovarSolicitacaoCadastroUsuarioImpl(UsuarioService usuarioService, SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository) {
        this.usuarioService = usuarioService;
        this.solicitacaoCadastroUsuarioRepository = solicitacaoCadastroUsuarioRepository;
    }

    @Override
    @Transactional
    public void realiza(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario) {
        usuarioService.criarUsuarioSolicitacaoCadastro(solicitacaoCadastroUsuario);
        solicitacaoCadastroUsuario.setStatus(StatusSolicitacao.APROVADA);
        solicitacaoCadastroUsuarioRepository.save(solicitacaoCadastroUsuario);
    }

    @Override
    public boolean statusSolicitacao(StatusSolicitacao solicitacao) {
        return solicitacao.equals(StatusSolicitacao.APROVADA);
    }


}
