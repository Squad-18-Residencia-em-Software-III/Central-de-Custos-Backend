package com.example.demo.domain.exceptions;

import java.util.List;

public class SenhaInvalidaException extends RuntimeException {
    private final List<String> erros;

    public SenhaInvalidaException(List<String> erros) {
        super("Senha inválida");
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}

