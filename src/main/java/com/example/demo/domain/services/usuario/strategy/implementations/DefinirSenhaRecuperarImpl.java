package com.example.demo.domain.services.usuario.strategy.implementations;

import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.exceptions.SenhaInvalidaException;
import com.example.demo.domain.services.usuario.strategy.DefinirSenhaUsuarioStrategy;
import com.example.demo.domain.validations.SenhaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DefinirSenhaRecuperarImpl implements DefinirSenhaUsuarioStrategy {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DefinirSenhaRecuperarImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void definirSenha(Usuario usuario, NovaSenhaDto senhaDto) {
        SenhaValidator.validarSenhaConfirma(senhaDto);
        List<String> errosSenha = SenhaValidator.verificarSenha(senhaDto.senha());
        if (!errosSenha.isEmpty()) {
            throw new SenhaInvalidaException(errosSenha);
        }

        usuario.setSenha(bCryptPasswordEncoder.encode(senhaDto.senha()));
        log.info("RECUPERAR_SENHA: Senha do usuario {} alterada com sucesso", usuario.getUuid());
    }

    @Override
    public TipoToken getTipoToken() {
        return TipoToken.RECUPERAR_SENHA;
    }
}
