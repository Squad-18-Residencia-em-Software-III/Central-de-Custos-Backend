package com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.services.usuario.UsuarioService;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AprovarSolicitacaoCadastroUsuarioImpl extends AceitarSolicitacaoCadastroStrategy {

    private final UsuarioService usuarioService;

    protected AprovarSolicitacaoCadastroUsuarioImpl(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository, UsuarioService usuarioService) {
        super(solicitacaoCadastroUsuarioRepository);
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public void realiza(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario) {
        usuarioService.criarUsuarioSolicitacaoCadastro(solicitacaoCadastroUsuario);
        solicitacaoCadastroUsuario.setStatus(StatusSolicitacao.APROVADA);
        solicitacaoCadastroUsuarioRepository.save(solicitacaoCadastroUsuario);
    }

    @Override
    public StatusSolicitacao getStatus() {
        return StatusSolicitacao.APROVADA;
    }


}
