package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.domain.Specification;

public class ComboSpecs {

    public static Specification<Combo> estruturaEqual(Estrutura estrutura){
        return (root, query, cb) -> cb.equal(root.get("estrutura"), estrutura);
    }

    public static Specification<Combo> competenciaEqual(Competencia competencia){
        return ((root, query, cb) -> cb.equal(root.get("competencia"), competencia));
    }

    public static Specification<Combo> comNomeContendo(String nome) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }


}
