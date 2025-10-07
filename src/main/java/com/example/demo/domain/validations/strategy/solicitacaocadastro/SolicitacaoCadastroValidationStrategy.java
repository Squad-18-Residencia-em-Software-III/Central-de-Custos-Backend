package com.example.demo.domain.validations.strategy.solicitacaocadastro;

import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;

public interface SolicitacaoCadastroValidationStrategy {

    void validar(SolicitaCadastroUsuarioDto solicitaCadastroUsuarioDto);
}
