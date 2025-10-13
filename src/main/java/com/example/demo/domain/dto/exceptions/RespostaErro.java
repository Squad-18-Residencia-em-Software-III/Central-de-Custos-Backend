package com.example.demo.domain.dto.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RespostaErro(int status, String mensagem, List<CamposErros> camposErros) {

    public static RespostaErro notFound(String mensagem){
        return new RespostaErro(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

    public static RespostaErro badRequest(String mensagem){
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static RespostaErro forbbiden(String mensagem){
        return new RespostaErro(HttpStatus.FORBIDDEN.value(), mensagem, List.of());
    }

    public static RespostaErro unauthorized(String mensagem){
        return new RespostaErro(HttpStatus.UNAUTHORIZED.value(), mensagem, List.of());
    }

    public static RespostaErro unprocessableEntity(String mensagem){
        return new RespostaErro(HttpStatus.UNPROCESSABLE_ENTITY.value(), mensagem, List.of());
    }

}
