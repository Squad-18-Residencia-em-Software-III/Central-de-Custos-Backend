package com.example.demo.domain.repositorios;

import com.example.demo.domain.dto.combos.item.ItemComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ValorItemComboRepository extends JpaRepository<ValorItemCombo, Long> {

    @Query(value = """
    SELECT 
        i.uuid AS uuid,
        i.nome AS nome,
        COALESCE(v.valor, 0.0) AS valor,
        v.uuid AS valor_uuid,
        i.unidade_medida AS unidade_medida,
        COALESCE(v.quantidade_unidade_medida, 0.0) AS quantidade_unidade_medida
    FROM combo c
    JOIN combo_item_combo cic ON cic.combo_id = c.id
    JOIN item_combo i ON i.id = cic.item_combo_id
    LEFT JOIN valor_item_combo v
        ON v.item_combo_id = i.id
        AND v.combo_id = c.id
        AND v.estrutura_id = :estruturaId
    WHERE c.id = :comboId
""", nativeQuery = true)
    List<Object[]> buscarItensDoComboComValores(
            @Param("estruturaId") Long estruturaId,
            @Param("comboId") Long comboId
    );


    Optional<ValorItemCombo> findByEstruturaAndComboAndItemCombo(Estrutura estrutura, Combo combo, ItemCombo itemCombo);

    boolean existsByItemCombo(ItemCombo itemCombo);

    Optional<ValorItemCombo> findByUuid(UUID valorItemComboId);

    // graficos

    @Query(value = """
    SELECT
        TO_CHAR(comp.competencia, 'YYYY-MM') AS competencia,
        COALESCE(SUM(vic.valor), 0) AS total_valor
    FROM competencia comp
    LEFT JOIN combo c
        ON c.competencia_id = comp.id
    LEFT JOIN valor_item_combo vic
        ON vic.combo_id = c.id
        AND vic.estrutura_id = :estruturaId
    WHERE comp.id = :competenciaId
    GROUP BY comp.competencia
    ORDER BY comp.competencia
    """, nativeQuery = true)
    List<Object[]> gastosTotaisPorCompetencia(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId
    );

    @Query(value = """
    SELECT
        ic.nome AS nome_item,
        SUM(vic.valor) AS valor_total_item
    FROM valor_item_combo vic
    JOIN combo cb
        ON cb.id = vic.combo_id
    JOIN item_combo ic
        ON ic.id = vic.item_combo_id
    JOIN estrutura e
        ON e.id = vic.estrutura_id
    JOIN competencia c
        ON c.id = cb.competencia_id
    WHERE e.id = 1
      AND c.id = 12
    GROUP BY ic.nome
    ORDER BY valor_total_item DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Object[]> itensMaisCarosPorEstrutura(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId
    );

    @Query(value = """
    WITH RECURSIVE estruturas_filhos AS (
            SELECT id
            FROM estrutura
            WHERE id = 2
            UNION ALL
            SELECT e.id
            FROM estrutura e
            INNER JOIN estruturas_filhos ef ON e.estrutura_pai_id = ef.id
    )
    SELECT
        ic.nome AS nome_item,
        SUM(vic.valor) AS valor_total_item
    FROM valor_item_combo vic
    JOIN combo cb ON cb.id = vic.combo_id
    JOIN item_combo ic ON ic.id = vic.item_combo_id
    JOIN estrutura e ON e.id = vic.estrutura_id
    JOIN competencia c ON c.id = cb.competencia_id
    WHERE e.id IN (
        SELECT id FROM estruturas_filhos WHERE id <> 2
    )
      AND c.id = 12
    GROUP BY ic.nome
    ORDER BY valor_total_item DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Object[]> itensMaisCarosPorEstruturaFilhos(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId
    );

    // custo por aluno

    @Query(value = """
            SELECT
            	TO_CHAR(c.competencia, 'YYYY-MM') AS competencia,
            	COALESCE(SUM(vic.valor), 0) AS total_valor,
            	COALESCE(cae.numero_alunos, 0) AS numero_alunos,
            	CASE
            		WHEN COALESCE(cae.numero_alunos, 0) > 0
            		THEN ROUND(COALESCE(SUM(vic.valor), 0) / cae.numero_alunos, 2)
            		ELSE 0
            	END AS custo_por_aluno
            FROM competencia c
            LEFT JOIN combo
             ON combo.competencia_id = c.id
            LEFT JOIN valor_item_combo vic
            	ON vic.combo_id = combo.id
            	AND vic.estrutura_id = :estruturaId
            LEFT JOIN competencia_aluno_estrutura cae
            	ON cae.competencia_id = c.id
            	AND cae.estrutura_id = :estruturaId
            WHERE c.id = :competenciaId
            GROUP BY c.competencia, cae.numero_alunos
            ORDER BY c.competencia
            """ ,nativeQuery = true)
    List<Object[]> custosPorAluno(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId
    );

    @Query(value = """
WITH current_competencia AS (
    SELECT id
    FROM competencia
    WHERE competencia.id = :competenciaId
    LIMIT 1
),
valor_por_escola AS (
    SELECT v.estrutura_id,
           SUM(v.valor) AS total_valor
    FROM valor_item_combo v
    JOIN combo cb ON cb.id = v.combo_id
    JOIN current_competencia cc ON cb.competencia_id = cc.id
    GROUP BY v.estrutura_id
),
alunos_por_escola AS (
    SELECT cae.estrutura_id,
           SUM(cae.numero_alunos) AS total_alunos
    FROM competencia_aluno_estrutura cae
    JOIN current_competencia cc ON cae.competencia_id = cc.id
    GROUP BY cae.estrutura_id
)
SELECT
    e_f.id AS escola_id,
    e_f.nome AS nome_escola,
    COALESCE(vpe.total_valor, 0) AS total_valor,
    COALESCE(ape.total_alunos, 0) AS numero_alunos,
    CASE
        WHEN COALESCE(ape.total_alunos, 0) > 0
        THEN ROUND(COALESCE(vpe.total_valor, 0) / NULLIF(ape.total_alunos, 0), 2)
        ELSE 0
    END AS custo_aluno
FROM estrutura e_f
JOIN estrutura e ON e.id = e_f.estrutura_pai_id
LEFT JOIN valor_por_escola vpe ON vpe.estrutura_id = e_f.id
LEFT JOIN alunos_por_escola ape ON ape.estrutura_id = e_f.id
WHERE e.id = :diretoriaId
  AND e_f.classificacao_estrutura = 'ESCOLA'
ORDER BY e_f.nome
""", nativeQuery = true)
    List<Object[]> findEscolasComCustoPorAluno(
            @Param("diretoriaId") Long diretoriaId,
            @Param("competenciaId") Long competenciaId
    );


}
