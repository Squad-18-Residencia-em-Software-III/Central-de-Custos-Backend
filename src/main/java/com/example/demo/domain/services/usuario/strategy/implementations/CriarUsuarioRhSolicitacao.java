package com.example.demo.domain.services.usuario.strategy.implementations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.services.token.TokensService;
import com.example.demo.domain.services.usuario.strategy.CriarUsuarioSolicitacaoStrategy;
import com.example.demo.domain.validations.UsuarioValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CriarUsuarioRhSolicitacao implements CriarUsuarioSolicitacaoStrategy {
    private final UsuarioValidator usuarioValidator;
    private final UsuarioRepository usuarioRepository;
    private final TokensService tokensService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioMapper usuarioMapper;

    public CriarUsuarioRhSolicitacao(UsuarioValidator usuarioValidator, UsuarioRepository usuarioRepository, TokensService tokensService, BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioValidator = usuarioValidator;
        this.usuarioRepository = usuarioRepository;
        this.tokensService = tokensService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public String getEstrutura() {
        return "RH";
    }

    @Override
    @Transactional
    public void criarUsuario(SolicitacaoCadastroUsuario solicitacao) {
        Usuario novoUsuario = usuarioMapper.toEntity(solicitacao);
        Perfil perfilRh = usuarioValidator.validaPerfilExisteNome("RH");
        novoUsuario.setPerfil(perfilRh);

        novoUsuario.setSenha(bCryptPasswordEncoder.encode(
                RandomStringUtils.secureStrong().nextAlphanumeric(5, 20)
        ));

        usuarioRepository.save(novoUsuario);
        tokensService.criarToken(novoUsuario, TipoToken.PRIMEIRO_ACESSO);
    }
}
