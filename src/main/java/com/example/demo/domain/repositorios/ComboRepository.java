package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.enums.StatusCompetencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComboRepository extends JpaRepository<Combo, Long>, JpaSpecificationExecutor<Combo> {
    Optional<Combo> findByUuid(UUID comboId);

    List<Combo> findAllByItensContaining(ItemCombo itemCombo);

    boolean existsByNomeAndCompetencia(String nome, Competencia competencia);

    @Query("""
SELECT DISTINCT c.competencia
FROM combo c
JOIN c.estruturas e
WHERE e.id = :estruturaId
AND (:status IS NULL OR c.competencia.statusCompetencia = :status)
""")
    Page<Competencia> findCompetenciasByEstruturaIdAndStatus(
            @Param("estruturaId") Long estruturaId,
            @Param("status") StatusCompetencia status,
            Pageable pageable
    );

}
