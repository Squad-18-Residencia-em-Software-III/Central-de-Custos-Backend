package com.example.demo.domain.services.usuario;

import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.services.AuthService;
import com.example.demo.domain.services.token.TokensService;
import com.example.demo.domain.services.usuario.factory.UsuarioFactory;
import com.example.demo.domain.validations.UsuarioValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioSenhaService {

    private final UsuarioValidator usuarioValidator;
    private final TokensService tokensService;
    private final UsuarioFactory usuarioFactory;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;

    public UsuarioSenhaService(UsuarioValidator usuarioValidator, TokensService tokensService, UsuarioFactory usuarioFactory, UsuarioRepository usuarioRepository, AuthService authService) {
        this.usuarioValidator = usuarioValidator;
        this.tokensService = tokensService;
        this.usuarioFactory = usuarioFactory;
        this.usuarioRepository = usuarioRepository;
        this.authService = authService;
    }

    @Transactional
    public AccessTokenDto definirSenha(String codigoToken, String cpf, NovaSenhaDto senhaDto){
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        Tokens token = tokensService.validarToken(codigoToken, cpf);

        usuarioFactory.tipoToken(token.getTipo()).definirSenha(usuario, senhaDto);
        usuarioRepository.save(usuario);
        tokensService.deletarToken(token);

        return authService.login(new LoginDto(cpf, senhaDto.senha()));
    }

}
