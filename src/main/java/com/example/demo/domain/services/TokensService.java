package com.example.demo.domain.services;

import com.example.demo.domain.enums.TipoToken;
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

    @Transactional
    public String criarTokenPrimeiroAcesso(Usuario usuario){
        Tokens tokenPrimeiroAcesso = new Tokens();
        tokenPrimeiroAcesso.setToken(gerarToken());
        tokenPrimeiroAcesso.setTipo(TipoToken.PRIMEIRO_ACESSO);
        tokenPrimeiroAcesso.setUsuario(usuario);
        tokenPrimeiroAcesso.setExpiraEm(LocalDateTime.now().plusDays(2));

        tokensRepository.save(tokenPrimeiroAcesso);
        return tokenPrimeiroAcesso.getToken();
    }

    @Transactional
    public String criarTokenRecuperarSenha(Usuario usuario){
        tokenValidator.validaTokenExistenteUsuario(usuario);
        usuarioValidator.validaUsuarioPrimeiroAcesso(usuario);

        Tokens tokenRecuperarSenha = new Tokens();
        tokenRecuperarSenha.setToken(gerarToken());
        tokenRecuperarSenha.setTipo(TipoToken.RECUPERAR_SENHA);
        tokenRecuperarSenha.setUsuario(usuario);
        tokenRecuperarSenha.setExpiraEm(LocalDateTime.now().plusMinutes(15));

        tokensRepository.save(tokenRecuperarSenha);
        return tokenRecuperarSenha.getToken();
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

    public static String gerarToken() {
        return RandomStringUtils.secureStrong().nextAlphanumeric(32, 40);
    }

}
