package com.example.demo.domain.services;

import com.example.demo.domain.entities.solicitacoes.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.TokensRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokensService {

    private final TokensRepository tokensRepository;

    public TokensService(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
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

}
