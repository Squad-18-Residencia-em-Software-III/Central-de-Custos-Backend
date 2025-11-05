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
        TO_CHAR(c.competencia, 'YYYY-MM') AS competencia,
        COALESCE(SUM(vic.valor), 0) AS total_valor
    FROM competencia c
    LEFT JOIN valor_item_combo vic 
        ON vic.competencia_id = c.id 
        AND vic.estrutura_id = :estruturaId
    WHERE EXTRACT(YEAR FROM c.competencia) = :ano
    GROUP BY c.competencia
    ORDER BY c.competencia
    """, nativeQuery = true)
    List<Object[]> gastosTotaisPorCompetenciaAno(
            @Param("estruturaId") Long estruturaId,
            @Param("ano") int ano
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
            LEFT JOIN valor_item_combo vic
                ON vic.competencia_id = c.id
                AND vic.estrutura_id = :estruturaId
            LEFT JOIN competencia_aluno_estrutura cae
                ON cae.competencia_id = c.id
                AND cae.estrutura_id = :estruturaId
            WHERE EXTRACT(YEAR FROM c.competencia) = :ano
            GROUP BY c.competencia, cae.numero_alunos
            ORDER BY c.competencia
            """ ,nativeQuery = true)
    List<Object[]> custosPorAluno(
            @Param("estruturaId") Long estruturaId,
            @Param("ano") int ano
    );
}
