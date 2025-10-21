package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EstruturaRepository extends JpaRepository<Estrutura, Long>, JpaSpecificationExecutor<Estrutura> {

    Optional<Estrutura> findByUuid(UUID uuid);

    Optional<Estrutura> findByNome(String nome);

    @Query(value = """
    WITH RECURSIVE hierarquia AS (
        SELECT id, estrutura_pai_id
        FROM estrutura
        WHERE id = :idBase
        UNION ALL
        SELECT e.id, e.estrutura_pai_id
        FROM estrutura e
        JOIN hierarquia h ON e.estrutura_pai_id = h.id
    )
    SELECT EXISTS (
        SELECT 1 FROM hierarquia WHERE id = :idAlvo
    )
    """, nativeQuery = true)
    boolean pertenceAHierarquia(@Param("idBase") Long idBase, @Param("idAlvo") Long idAlvo);

    Optional<Estrutura> findByClassificacaoEstrutura(ClassificacaoEstrutura classificacaoEstrutura);
}
