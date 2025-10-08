package com.example.demo.domain.services.usuario;

import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.exceptions.SenhaInvalidaException;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.services.AuthService;
import com.example.demo.domain.services.token.TokensService;
import com.example.demo.domain.services.usuario.strategy.CriarUsuarioSolicitacaoStrategy;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.SenhaValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioValidator usuarioValidator;
    private final TokensService tokensService;
    private final UsuarioSenhaService usuarioSenhaService;
    private final UsuarioCriarService usuarioCriarService;

    public UsuarioService(UsuarioValidator usuarioValidator, TokensService tokensService, UsuarioSenhaService usuarioSenhaService, UsuarioCriarService usuarioCriarService) {
        this.usuarioValidator = usuarioValidator;
        this.tokensService = tokensService;
        this.usuarioSenhaService = usuarioSenhaService;
        this.usuarioCriarService = usuarioCriarService;
    }

    @Transactional
    public void criarUsuarioSolicitacaoCadastro(SolicitacaoCadastroUsuario solicitacao){
        usuarioCriarService.criarUsuarioSolicitacaoCadastro(solicitacao);
    }

    @Transactional
    public AccessTokenDto defineNovaSenhaUsuario(String codigoToken, String cpf, NovaSenhaDto senhaDto){
        return usuarioSenhaService.definirSenha(codigoToken, cpf, senhaDto);
    }

    public void solicitaRecuperarSenha(String cpf){
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        tokensService.criarToken(usuario, TipoToken.RECUPERAR_SENHA); // cria token
    }
}
