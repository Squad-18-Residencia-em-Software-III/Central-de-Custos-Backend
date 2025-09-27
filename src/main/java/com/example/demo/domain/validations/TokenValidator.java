package com.example.demo.domain.validations;

import com.example.demo.domain.entities.solicitacoes.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.TokensRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenValidator {

    private final TokensRepository tokensRepository;

    public TokenValidator(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    public Tokens validaTokenExiste(String token){
        return tokensRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token inválido ou inexistente"));
    }

    public void validaTokenExistenteUsuario(Usuario usuario){
        if (tokensRepository.existsByUsuario(usuario)){
            throw new AccessDeniedException("Já existe um link de recuperação de senha ativo para esse usuario.");
        }
    }

    @Transactional
    public void validaTokenExpirado(Tokens token){
        if (token.getExpiraEm().isBefore(LocalDateTime.now())){
            tokensRepository.delete(token);
            throw new AccessDeniedException("Link expirado, favor solicitar um novo");
        }
    }

    public void validaTipoToken(Tokens token, TipoToken tipoToken){
        if (!token.getTipo().equals(tipoToken)){
            throw new AccessDeniedException("Tipo de token Incorreto");
        }
    }

}
