package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import org.springframework.data.jpa.domain.Specification;

public class EstruturaSpecs {

    public static Specification<Estrutura> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Estrutura> pertenceAEstruturaPai(Estrutura estruturaPai) {
        return (root, query, cb) ->
                cb.equal(root.get("estruturaPai"), estruturaPai);
    }

    public static Specification<Estrutura> comClassificacao(ClassificacaoEstrutura classificacao) {
        return (root, query, cb) ->
                cb.equal(root.get("classificacaoEstrutura"), classificacao);
    }

}
