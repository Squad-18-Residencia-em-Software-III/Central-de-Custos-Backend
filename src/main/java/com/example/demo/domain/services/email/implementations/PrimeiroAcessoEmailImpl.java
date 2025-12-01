package com.example.demo.domain.services.email.implementations;

import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoEmail;
import com.example.demo.domain.services.email.EmailServiceSender;
import com.example.demo.domain.services.email.EmailServiceStrategy;
import org.springframework.stereotype.Service;

@Service
public class PrimeiroAcessoEmailImpl extends EmailServiceStrategy {

    private static final String PRIMEIRO_ACESSO_SUBJECT = "Primeiro Acesso - Central de custos SEDUC";
    private static final String PRIMEIRO_ACESSO_BODY_TEMPLATE =
            "Olá %s%n%n" +
                    "Seu cadastro na plataforma foi aceito!%n%n" +
                    "Para fazer o seu primeiro acesso e definir sua senha, acesse o link abaixo:%n%n" +
                    "http://localhost:3000/token/validar/%s%n" +
                    "(Atenção! Esse link tem duração de 48h. Após esse prazo, será necessário solicitar um novo cadastro)";

    public PrimeiroAcessoEmailImpl(EmailServiceSender emailServiceSender) {
        super(emailServiceSender);
    }

    @Override
    public TipoEmail getTipo() {
        return TipoEmail.PRIMEIRO_ACESSO;
    }

    @Override
    public void montarEmail(Usuario usuario, String token) {
        String message = String.format(PRIMEIRO_ACESSO_BODY_TEMPLATE,
                usuario.getNome(),
                token
        );
        enviarEmail(gerarEmail(usuario.getEmail(), PRIMEIRO_ACESSO_SUBJECT, message));
    }


}
