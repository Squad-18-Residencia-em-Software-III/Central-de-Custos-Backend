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
import java.util.Optional;

public interface ValorItemComboRepository extends JpaRepository<ValorItemCombo, Long> {

    @Query(value = """
    SELECT 
        i.uuid AS uuid,
        i.nome AS nome,
        COALESCE(v.valor, 0.0) AS valor,
        v.uuid AS valor_uuid
    FROM combo c
    JOIN combo_item_combo cic ON cic.combo_id = c.id
    JOIN item_combo i ON i.id = cic.item_combo_id
    LEFT JOIN valor_item_combo v
        ON v.item_combo_id = i.id
        AND v.combo_id = c.id
        AND v.estrutura_id = :estruturaId
        AND v.competencia_id = :competenciaId
    WHERE c.id = :comboId
""", nativeQuery = true)
    List<Object[]> buscarItensDoComboComValores(
            @Param("estruturaId") Long estruturaId,
            @Param("competenciaId") Long competenciaId,
            @Param("comboId") Long comboId
    );


    Optional<ValorItemCombo> findByEstruturaAndComboAndItemComboAndCompetencia(Estrutura estrutura, Combo combo, ItemCombo itemCombo, Competencia competencia);

    boolean existsByItemCombo(ItemCombo itemCombo);
}
