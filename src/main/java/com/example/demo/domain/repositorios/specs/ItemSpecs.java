package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.enums.UnidadeMedida;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecs {

    public static Specification<ItemCombo> comNomeContendo(String nome) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<ItemCombo> daUnidadeMedida(UnidadeMedida unidadeMedida) {
        return (root, query, cb) -> cb.equal(root.get("unidadeMedida"), unidadeMedida);
    }

}
