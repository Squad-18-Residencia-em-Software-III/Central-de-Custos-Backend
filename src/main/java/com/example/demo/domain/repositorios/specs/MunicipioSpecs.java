package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.Municipio;
import org.springframework.data.jpa.domain.Specification;

public class MunicipioSpecs {

    public static Specification<Municipio> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

}
