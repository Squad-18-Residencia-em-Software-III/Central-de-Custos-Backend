package com.example.demo.domain.services.token.implementations;

import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.repositorios.TokensRepository;
import com.example.demo.domain.services.token.strategy.CriarTokenStrategy;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CriarTokenPrimeiroAcessoImpl implements CriarTokenStrategy {

    private final TokensRepository tokensRepository;

    public CriarTokenPrimeiroAcessoImpl(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    @Override
    public String geraToken(Usuario usuario) {
        Tokens tokenPrimeiroAcesso = new Tokens();
        tokenPrimeiroAcesso.setToken(gerarToken());
        tokenPrimeiroAcesso.setTipo(TipoToken.PRIMEIRO_ACESSO);
        tokenPrimeiroAcesso.setUsuario(usuario);
        tokenPrimeiroAcesso.setExpiraEm(LocalDateTime.now().plusDays(2));

        tokensRepository.save(tokenPrimeiroAcesso);
        return tokenPrimeiroAcesso.getToken();
    }

    public static String gerarToken() {
        return RandomStringUtils.secureStrong().nextAlphanumeric(32, 40);
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.PRIMEIRO_ACESSO;
    }
}
