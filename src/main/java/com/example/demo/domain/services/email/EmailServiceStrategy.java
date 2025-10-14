package com.example.demo.domain.services.email;

import com.example.demo.domain.dto.email.EmailDto;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoEmail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EmailServiceStrategy {

    protected final EmailServiceSender emailServiceSender;

    protected EmailServiceStrategy(EmailServiceSender emailServiceSender) {
        this.emailServiceSender = emailServiceSender;
    }

    public abstract TipoEmail getTipo();

    public void enviarEmail(EmailDto dto) {
        try {
            emailServiceSender.sendEmail(dto);
            log.info("Email para {} enviado com sucesso", dto.receiver());
        } catch (Exception e){
            log.error("EMAIL NÃO ENVIADO! (SERVIÇO DE EMAIL COM PROBLEMA OU INATIVO)", e);
        }
    }

    public abstract void montarEmail(Usuario usuario, String token);

    public EmailDto gerarEmail(String receiver, String subject, String message){
        return new EmailDto(receiver, subject, message);
    }
}
