package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.domain.Specification;

public class ValorItemSpecs {

    public static Specification<ValorItemCombo> doCombo(Combo combo) {
        return (root, query, cb) -> cb.equal(root.get("combo"), combo);
    }

    public static Specification<ValorItemCombo> daEstrutura(Estrutura estrutura) {
        return (root, query, cb) -> cb.equal(root.get("estrutura"), estrutura);
    }

    public static Specification<ValorItemCombo> daCompetencia(Competencia competencia) {
        return (root, query, cb) -> cb.equal(root.get("competencia"), competencia);
    }

}
