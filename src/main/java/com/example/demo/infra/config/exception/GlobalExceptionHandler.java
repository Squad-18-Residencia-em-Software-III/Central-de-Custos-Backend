package com.example.demo.infra.config.exception;

import com.example.demo.domain.dto.exceptions.CamposErros;
import com.example.demo.domain.dto.exceptions.RespostaErro;
import com.example.demo.domain.exceptions.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public RespostaErro handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<CamposErros> listaDeErros = fieldErrors
                .stream()
                .map(fe -> new CamposErros(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new RespostaErro(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaDeErros
        );
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleSenhaInvalidaException(SenhaInvalidaException e) {
        List<CamposErros> listaDeErros = e.getErros().stream()
                .map(msg -> new CamposErros("senha", msg)) // campo "senha"
                .toList();

        return new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação de senha",
                listaDeErros
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespostaErro handleMethodArgumentNotFoundException(EntityNotFoundException e){
        return RespostaErro.notFound(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleMethodIllegalArgumentException(IllegalArgumentException e){
        return RespostaErro.badRequest(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleMethodAccessDeniedException(AccessDeniedException e){
        return RespostaErro.badRequest(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RespostaErro handleMethodBadCredentialsException(BadCredentialsException e){
        return RespostaErro.forbbiden(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespostaErro handleInternalServerErrors(RuntimeException e){
        System.out.println(e.getMessage());
        return new RespostaErro(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro desconhecido, entre em contato com o Suporte",
                List.of()
        );
    }
}
