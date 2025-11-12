package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

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

    public static Specification<Estrutura> naoPossuiCombo(UUID comboUuid) {
        return (root, query, cb) -> {
            if (comboUuid == null) return null;

            assert query != null;
            var subquery = query.subquery(Long.class);
            var subRoot = subquery.from(Estrutura.class);
            var joinCombo = subRoot.join("combos");
            subquery.select(cb.literal(1L))
                    .where(
                            cb.equal(subRoot.get("id"), root.get("id")),
                            cb.equal(joinCombo.get("uuid"), comboUuid)
                    );

            return cb.not(cb.exists(subquery));
        };
    }

}
