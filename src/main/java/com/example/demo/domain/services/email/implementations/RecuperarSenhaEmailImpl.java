package com.example.demo.domain.services.email.implementations;

import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoEmail;
import com.example.demo.domain.services.email.EmailServiceSender;
import com.example.demo.domain.services.email.EmailServiceStrategy;
import org.springframework.stereotype.Service;

@Service
public class RecuperarSenhaEmailImpl extends EmailServiceStrategy {

    private static final String RECUPERAR_SENHA_SUBJECT = "Recuperar Senha - Central de custos SEDUC";
    private static final String RECUPERAR_SENHA_BODY_TEMPLATE =
            "Olá %s%n%n" +
                    "Uma solicitação de recuperação de senha foi feita para o seu usuário%n%n" +
                    "Para resetar sua senha atual e definir uma nova, acesse o link abaixo:%n%n" +
                    "http://localhost:3000/token/validar/%s%n" +
                    "(Atenção! Esse link tem duração de 15min. Após esse prazo, será necessário solicitar novamente)";


    protected RecuperarSenhaEmailImpl(EmailServiceSender emailServiceSender) {
        super(emailServiceSender);
    }

    @Override
    public TipoEmail getTipo() {
        return TipoEmail.RECUPERAR_SENHA;
    }

    @Override
    public void montarEmail(Usuario usuario, String token) {
        String message = String.format(RECUPERAR_SENHA_BODY_TEMPLATE,
                usuario.getNome(),
                token
        );
        enviarEmail(gerarEmail(usuario.getEmail(), RECUPERAR_SENHA_SUBJECT, message));
    }
}
