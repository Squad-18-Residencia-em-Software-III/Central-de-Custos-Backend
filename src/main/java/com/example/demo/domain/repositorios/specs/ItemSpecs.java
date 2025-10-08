package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.combos.ItemCombo;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecs {

    public static Specification<ItemCombo> comNomeContendo(String nome) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

}
