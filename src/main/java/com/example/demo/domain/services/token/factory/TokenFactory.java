package com.example.demo.domain.services.token.factory;

import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.services.token.strategy.CriarTokenStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TokenFactory {

    private final Map<TipoToken, CriarTokenStrategy> strategies = new HashMap<>();

    public TokenFactory(Set<CriarTokenStrategy> strategySet){
        strategySet.forEach(s -> strategies.put(s.getTipoToken(), s));
    }

    public CriarTokenStrategy criarToken(TipoToken tipoToken) throws EntityNotFoundException {
        CriarTokenStrategy strategy = strategies.get(tipoToken);
        if (strategy == null){
            throw new EntityNotFoundException("Tipo não encontrado ou inválido");
        }
        return strategy;
    }

}
