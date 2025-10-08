package com.example.demo.domain.services.solicitacoes.cadastrousuario.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.services.UsuarioService;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import org.springframework.stereotype.Component;

@Component
public class AprovarSolicitacaoCadastroCadastroUsuarioImpl implements AceitarSolicitacaoCadastroStrategy {

    private final UsuarioService usuarioService;
    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository;

    public AprovarSolicitacaoCadastroCadastroUsuarioImpl(UsuarioService usuarioService, SolicitacaoCadastroUsuarioRepository solicitacaoCadastroUsuarioRepository) {
        this.usuarioService = usuarioService;
        this.solicitacaoCadastroUsuarioRepository = solicitacaoCadastroUsuarioRepository;
    }

    @Override
    public void escolha(SolicitacaoCadastroUsuario solicitacaoCadastroUsuario) {
        usuarioService.criarUsuarioSolicitacaoCadastro(solicitacaoCadastroUsuario);
        solicitacaoCadastroUsuario.setStatus(StatusSolicitacao.APROVADA);
        solicitacaoCadastroUsuarioRepository.save(solicitacaoCadastroUsuario);
    }

}
