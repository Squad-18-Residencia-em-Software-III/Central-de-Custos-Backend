package com.example.demo.domain.services.email.factory;

import com.example.demo.domain.enums.TipoEmail;
import com.example.demo.domain.services.email.EmailServiceStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class EmailFactory {

    private final Map<TipoEmail, EmailServiceStrategy> strategies = new HashMap<>();

    public EmailFactory(Set<EmailServiceStrategy> strategies) {
        strategies.forEach(strategy -> this.strategies.put(strategy.getTipo(), strategy));
    }

    public EmailServiceStrategy getStrategy(TipoEmail tipo) {
        EmailServiceStrategy strategy = strategies.get(tipo);
        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de e-mail não suportado: " + tipo);
        }
        return strategy;
    }

}
