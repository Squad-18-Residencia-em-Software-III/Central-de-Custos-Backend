package com.example.demo.domain.services.token;

import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.TokensRepository;
import com.example.demo.domain.services.token.factory.TokenFactory;
import com.example.demo.domain.validations.TokenValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokensService {

    private final TokensRepository tokensRepository;
    private final TokenValidator tokenValidator;
    private final UsuarioValidator usuarioValidator;
    private final TokenFactory tokenFactory;

    public TokensService(TokensRepository tokensRepository, TokenValidator tokenValidator, UsuarioValidator usuarioValidator, TokenFactory tokenFactory) {
        this.tokensRepository = tokensRepository;
        this.tokenValidator = tokenValidator;
        this.usuarioValidator = usuarioValidator;
        this.tokenFactory = tokenFactory;
    }

    @Transactional
    public String criarToken(Usuario usuario, TipoToken tipoToken){
        return tokenFactory.criarToken(tipoToken).geraToken(usuario);
    }

    public Tokens validarToken(String codigoToken, String cpf){
        usuarioValidator.validaUsuarioExisteCpf(cpf);
        Tokens token = tokenValidator.validaTokenExiste(codigoToken);
        tokenValidator.validaTokenExpirado(token);
        log.info("Token {} válido", codigoToken);
        return token;
    }

    @Transactional
    public void deletarToken(Tokens token){
        tokensRepository.delete(token);
    }


}
