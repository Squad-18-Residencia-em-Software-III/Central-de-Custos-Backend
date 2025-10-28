package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.CompetenciaAlunoEstrutura;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CompetenciaAlunoEstruturaRepository extends JpaRepository<CompetenciaAlunoEstrutura, Long>, JpaSpecificationExecutor<CompetenciaAlunoEstrutura> {
    Optional<CompetenciaAlunoEstrutura> findByEstruturaAndCompetencia(Estrutura estrutura, Competencia competencia);

}
