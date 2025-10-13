package com.example.demo.domain.services.usuario.factory;

import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.services.token.strategy.CriarTokenStrategy;
import com.example.demo.domain.services.usuario.strategy.CriarUsuarioSolicitacaoStrategy;
import com.example.demo.domain.services.usuario.strategy.DefinirSenhaUsuarioStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UsuarioFactory {

    private final Map<TipoToken, DefinirSenhaUsuarioStrategy> definirSenhaStrategies = new HashMap<>();
    private final Map<String, CriarUsuarioSolicitacaoStrategy> criacaoUsuarioStrategies = new HashMap<>();

    public UsuarioFactory(Set<DefinirSenhaUsuarioStrategy> definirSenhaStrategySet, Set<CriarUsuarioSolicitacaoStrategy> criarUsuarioSolicitacaoStrategySet){
        definirSenhaStrategySet.forEach(s -> definirSenhaStrategies.put(s.getTipoToken(), s));
        criarUsuarioSolicitacaoStrategySet.forEach(s -> criacaoUsuarioStrategies.put(s.getEstrutura(), s));
    }

    public DefinirSenhaUsuarioStrategy tipoToken(TipoToken tipoToken) {
        DefinirSenhaUsuarioStrategy strategy = definirSenhaStrategies.get(tipoToken);
        if (strategy == null){
            throw new EntityNotFoundException("Tipo não encontrado ou inválido");
        }
        return strategy;
    }

    public CriarUsuarioSolicitacaoStrategy criarUsuarioPorEstrutura(String nomeEstrutura) {
        CriarUsuarioSolicitacaoStrategy strategy = criacaoUsuarioStrategies.get(nomeEstrutura);
        if (strategy == null){
            // pega a estrutura com DEFAULT
            strategy = criacaoUsuarioStrategies.get("DEFAULT");
            // SE NÃO POSSUIR NENHUMA DEFAULT
            if (strategy == null){
                throw new EntityNotFoundException("Estrutura não encontrado ou inválido");
            }
        }
        return strategy;
    }

}
