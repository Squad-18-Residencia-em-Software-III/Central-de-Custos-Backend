package com.example.demo.domain.services;

import com.example.demo.domain.entities.solicitacoes.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.TokensRepository;
import com.example.demo.domain.validations.TokenValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TokensService {

    private final TokensRepository tokensRepository;
    private final TokenValidator tokenValidator;
    private final UsuarioValidator usuarioValidator;

    public TokensService(TokensRepository tokensRepository, TokenValidator tokenValidator, UsuarioValidator usuarioValidator) {
        this.tokensRepository = tokensRepository;
        this.tokenValidator = tokenValidator;
        this.usuarioValidator = usuarioValidator;
    }

    public String criarTokenPrimeiroAcesso(Usuario usuario){
        Tokens tokenPrimeiroAcesso = new Tokens();
        tokenPrimeiroAcesso.setToken(RandomStringUtils.secureStrong().nextAlphanumeric(5, 10));
        tokenPrimeiroAcesso.setTipo(TipoToken.PRIMEIRO_ACESSO);
        tokenPrimeiroAcesso.setUsuario(usuario);
        tokenPrimeiroAcesso.setExpiraEm(LocalDateTime.now().plusDays(2));

        tokensRepository.save(tokenPrimeiroAcesso);
        return tokenPrimeiroAcesso.getToken();
    }

    public Tokens validarToken(String codigoToken, String cpf, TipoToken tipoToken){
        usuarioValidator.validaUsuarioExisteCpf(cpf);
        Tokens token = tokenValidator.validaTokenExiste(codigoToken);
        tokenValidator.validaTokenExpirado(token);
        tokenValidator.validaTipoToken(token, tipoToken);
        log.info("Token {} válido", codigoToken);
        return token;
    }

    @Transactional
    public void deletarToken(Tokens token){
        tokensRepository.delete(token);
    }

}
