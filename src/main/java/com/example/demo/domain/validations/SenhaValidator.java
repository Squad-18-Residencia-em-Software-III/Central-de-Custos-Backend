package com.example.demo.domain.validations;

import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SenhaValidator {

    public static List<String> verificarSenha(String senha) {

        List<String> erros = new ArrayList<>();

        validarTamanho(erros, senha);
        validarLetraMaiusculaMinuscula(erros, senha);
        validarDigitoNumerico(erros, senha);
        validarCaracterEspecial(erros, senha);

        return erros;
    }

    public static void validarSenhaConfirma(NovaSenhaDto dto){
        if (!dto.senha().equals(dto.confirmarSenha())){
            throw new IllegalArgumentException("Senhas não conferem");
        }
    }

    public static void validarTamanho(List<String> erros, String senha){
        if (senha != null && senha.length() < 8){
            erros.add("A senha deve possuir pelo menos 08 caracteres");
        }
    }

    public static void validarLetraMaiusculaMinuscula(List<String> erros, String senha){
        if (!Pattern.matches(".*[A-Z].*", senha)){ // essa lógica será aplicada em qualquer texto antes e depois do .* SE NAO TIVER UMA LETRA MAISCULA
            erros.add("A senha deve conter pelo menos uma letra maiúscula");
        }

        if (!Pattern.matches(".*[a-z].*", senha)){
            erros.add("A senha deve conter pelo menos uma letra minuscula");
        }
    }

    public static void validarDigitoNumerico(List<String> erros, String senha){
        if (!Pattern.matches(".*[0-9].*", senha)){
            erros.add("A senha deve conter pelo menos um dígito numérico");
        }
    }

    public static void validarCaracterEspecial(List<String> erros, String senha){
        if (!Pattern.matches(".*[\\W].*", senha)){
            erros.add("A senha deve conter pelo menos um caractere especial (e.g, !@#$%)");
        }
    }
}
