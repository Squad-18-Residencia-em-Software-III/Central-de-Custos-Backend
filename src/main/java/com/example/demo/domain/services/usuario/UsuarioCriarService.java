package com.example.demo.domain.services.usuario;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.services.usuario.factory.UsuarioFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioCriarService {

    private final UsuarioFactory usuarioFactory;

    public UsuarioCriarService(UsuarioFactory usuarioFactory) {
        this.usuarioFactory = usuarioFactory;
    }

    @Transactional
    public void criarUsuarioSolicitacaoCadastro(SolicitacaoCadastroUsuario solicitacao) {
        usuarioFactory.criarUsuarioPorEstrutura(solicitacao.getEstrutura().getNome()).criarUsuario(solicitacao);
    }

}
