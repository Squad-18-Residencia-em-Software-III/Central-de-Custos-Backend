package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long>{

    Optional<FolhaPagamento> findByEstruturaAndCompetencia(Estrutura estrutura, Competencia competencia);
}
