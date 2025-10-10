package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.domain.Specification;

public class EstruturaSpecs {

    public static Specification<Estrutura> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

}
