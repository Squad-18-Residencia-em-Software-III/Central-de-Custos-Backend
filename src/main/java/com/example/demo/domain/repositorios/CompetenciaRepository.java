package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.competencia.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long>, JpaSpecificationExecutor<Competencia> {
    Optional<Competencia> findByUuid(UUID competenciaId);

    Competencia findByCompetencia(LocalDate hoje);

    boolean existsByCompetencia(LocalDate localDate);

    //dashboard

    @Query(value = """
    SELECT
        c.competencia AS competencia_atual,
        (SELECT COUNT(*) FROM estrutura) AS quantidade_setores,
        NULL AS quantidade_escolas,
        NULL AS quantidade_alunos,
        COALESCE(SUM(vic.valor), 0) AS valor_total_competencia,
        NULL AS custo_por_aluno
    FROM competencia c
    JOIN combo cb ON cb.competencia_id = c.id
    LEFT JOIN valor_item_combo vic ON vic.combo_id = cb.id
    WHERE c.id = :competenciaId
    GROUP BY c.competencia
    """, nativeQuery = true)
    Object findHeaderForSecretaria(@Param("competenciaId") Long competenciaId);


    @Query(value = """
WITH current_competencia AS (
    SELECT id, competencia
    FROM competencia
    WHERE competencia.id = :competenciaId
    LIMIT 1
),
-- total de alunos das escolas filhas da diretoria na competência atual
alunos_cte AS (
    SELECT SUM(cae.numero_alunos) AS total_alunos
    FROM competencia_aluno_estrutura cae
    JOIN current_competencia cc ON cae.competencia_id = cc.id
    JOIN estrutura e_filha ON e_filha.id = cae.estrutura_id
    WHERE e_filha.estrutura_pai_id = :diretoriaId
),
-- total de valor dos combos das escolas filhas da diretoria na competência atual
valor_cte AS (
    SELECT SUM(vic.valor) AS total_valor
    FROM valor_item_combo vic
    JOIN combo cb ON cb.id = vic.combo_id
    JOIN current_competencia cc ON cb.competencia_id = cc.id
    JOIN estrutura e_filha ON e_filha.id = vic.estrutura_id
    WHERE e_filha.estrutura_pai_id = :diretoriaId
),
-- quantidade de escolas filhas da diretoria
escolas_cte AS (
    SELECT COUNT(DISTINCT e_filha.id) AS total_escolas
    FROM estrutura e_filha
    WHERE e_filha.estrutura_pai_id = :diretoriaId
)
SELECT
    (SELECT competencia FROM current_competencia) AS competencia_atual,
    NULL AS quantidade_setores,
    (SELECT total_escolas FROM escolas_cte) AS quantidade_escolas,
    COALESCE((SELECT total_alunos FROM alunos_cte), 0) AS quantidade_alunos,
    COALESCE((SELECT total_valor FROM valor_cte), 0) AS valor_total_competencia,
    NULL AS custo_por_aluno
""", nativeQuery = true)
    Object findHeaderForDiretoria(
            @Param("diretoriaId") Long diretoriaId,
            @Param("competenciaId") Long competenciaId
    );


    @Query(value = """
    SELECT
            c.competencia AS competencia_atual,
            NULL AS quantidade_setores,
            NULL AS quantidade_escolas,
            COALESCE(cae.numero_alunos, 0) AS quantidade_alunos,
            NULL AS valor_total_competencia,
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
""", nativeQuery = true)
    Object findHeaderForEscola(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId
    );

}
