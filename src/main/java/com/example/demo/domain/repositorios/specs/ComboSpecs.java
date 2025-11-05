package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ComboSpecs {

    public static Specification<Combo> estruturaEqual(Estrutura estrutura) {
        return (root, query, cb) -> {
            // Evita duplicados quando há JOIN
            query.distinct(true);

            Join<Object, Object> join = root.join("estruturas", JoinType.INNER); //faz o join com a tabela intermediária combo_estrutura.
            return cb.equal(join, estrutura); // compara o objeto estrutura diretamente.
        };
    }

    public static Specification<Combo> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Combo> daCompetencia(Competencia competencia) {
        return (root, query, cb) -> cb.equal(root.get("competencia"), competencia);
    }

}
