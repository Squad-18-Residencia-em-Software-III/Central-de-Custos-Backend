package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SolicitacaoInternaRepository extends JpaRepository<SolicitacaoInterna, Long>, JpaSpecificationExecutor<SolicitacaoInterna> {
}
