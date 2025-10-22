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

    @Query(value = """
        WITH estrutura_principal AS (
            SELECT 
                e.uuid AS id,
                e.nome,
                e.classificacao_estrutura,
                m.nome AS municipio,
                u.sigla AS uf,
                e.telefone,
                e.logradouro,
                e.complemento,
                e.numero_rua,
                e.bairro,
                e.cep
            FROM estrutura e
            JOIN municipio m ON m.id = e.municipio_id
            JOIN uf u ON u.id = m.uf_id
            WHERE e.uuid = :estruturaUuid
        )
        SELECT 
            jsonb_build_object(
                'id', ep.id,
                'nome', ep.nome,
                'classificacaoEstrutura', ep.classificacao_estrutura,
                'municipio', ep.municipio,
                'uf', ep.uf,
                'telefone', ep.telefone,
                'logradouro', ep.logradouro,
                'complemento', ep.complemento,
                'numeroRua', ep.numero_rua,
                'bairro', ep.bairro,
                'cep', ep.cep,
                'subSetores', COALESCE(
                    jsonb_agg(
                        jsonb_build_object(
                            'id', es.uuid,
                            'nome', es.nome,
                            'municipio', m2.nome,
                            'uf', u2.sigla,
                            'classificacaoEstrutura', es.classificacao_estrutura
                        )
                    ) FILTER (WHERE es.id IS NOT NULL),
                    '[]'::jsonb
                )
            ) AS estrutura_info
        FROM estrutura_principal ep
        LEFT JOIN estrutura es ON es.estrutura_pai_id = (
            SELECT id FROM estrutura WHERE uuid = :estruturaUuid
        )
        LEFT JOIN municipio m2 ON m2.id = es.municipio_id
        LEFT JOIN uf u2 ON u2.id = m2.uf_id
        GROUP BY ep.id, ep.nome, ep.classificacao_estrutura, ep.municipio, ep.uf, ep.telefone, ep.logradouro, ep.complemento, ep.numero_rua, ep.bairro, ep.cep
        """, nativeQuery = true)
    String buscarInfoEstruturaJson(@Param("estruturaUuid") UUID estruturaUuid);

    Optional<Estrutura> findByClassificacaoEstrutura(ClassificacaoEstrutura classificacaoEstrutura);
}
