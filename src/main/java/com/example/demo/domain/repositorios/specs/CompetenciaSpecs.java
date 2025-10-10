package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.enums.StatusCompetencia;
import org.springframework.data.jpa.domain.Specification;

public class CompetenciaSpecs {

    public static Specification<Competencia> doStatus(StatusCompetencia statusCompetencia) {
        return (root, query, cb) -> cb.equal(root.get("statusCompetencia"), statusCompetencia);
    }

}
