package com.example.demo.domain.services.token.implementations;

import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.repositorios.TokensRepository;
import com.example.demo.domain.services.token.strategy.CriarTokenStrategy;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CriarTokenRecuperarSenha implements CriarTokenStrategy {

    private final TokensRepository tokensRepository;

    public CriarTokenRecuperarSenha(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    @Override
    @Transactional
    public String geraToken(Usuario usuario) {
        Tokens tokenRecuperarSenha = new Tokens();
        tokenRecuperarSenha.setToken(gerarToken());
        tokenRecuperarSenha.setTipo(TipoToken.RECUPERAR_SENHA);
        tokenRecuperarSenha.setUsuario(usuario);
        tokenRecuperarSenha.setExpiraEm(LocalDateTime.now().plusMinutes(15));

        tokensRepository.save(tokenRecuperarSenha);
        return tokenRecuperarSenha.getToken();
    }

    public static String gerarToken() {
        return RandomStringUtils.secureStrong().nextAlphanumeric(32, 40);
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.RECUPERAR_SENHA;
    }

}
