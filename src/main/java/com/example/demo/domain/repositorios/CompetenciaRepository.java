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

    Optional<Competencia> findByCompetencia(LocalDate hoje);

    boolean existsByCompetencia(LocalDate localDate);

    @Query(value = """
        SELECT
            c.competencia AS competencia_atual,
            COUNT(DISTINCT e.id) AS quantidade_setores,
            NULL AS quantidade_escolas,
            NULL AS quantidade_alunos,
            COALESCE(SUM(vic.valor), 0) AS valor_total_competencia
        FROM competencia c
        JOIN combo cb ON cb.competencia_id = c.id
        JOIN valor_item_combo vic ON vic.combo_id = cb.id
        JOIN estrutura e ON e.id = vic.estrutura_id
        WHERE c.status = 'ABERTA'
        AND c.competencia = (
            SELECT competencia
            FROM competencia
            WHERE status = 'ABERTA'
            ORDER BY competencia DESC
            LIMIT 1
        )
        GROUP BY c.competencia
        """,
            nativeQuery = true)
    Object findHeaderForSecretaria();

    @Query(value = """
        SELECT
            c.competencia AS competencia_atual,
            COUNT(DISTINCT e_filha.id) AS quantidade_escolas,
            COALESCE(SUM(cae.numero_alunos), 0) AS quantidade_alunos,
            COUNT(DISTINCT e.id) AS quantidade_setores,
            COALESCE(SUM(vic.valor), 0) AS valor_total_competencia
        FROM estrutura e
        JOIN estrutura e_filha ON e_filha.estrutura_pai_id = e.id
        LEFT JOIN competencia_aluno_estrutura cae
            ON cae.estrutura_id = e_filha.id
        LEFT JOIN competencia c ON c.id = cae.competencia_id
        LEFT JOIN valor_item_combo vic
            ON vic.estrutura_id IN (e.id, e_filha.id)
        WHERE e.id = :diretoriaId
        AND c.status = 'ABERTA'
        GROUP BY c.competencia
        """,
            nativeQuery = true)
    Object findHeaderForDiretoria(@Param("diretoriaId") Long diretoriaId);
}
