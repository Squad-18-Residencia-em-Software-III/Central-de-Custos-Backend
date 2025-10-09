package com.example.demo.domain.repositorios;

import com.example.demo.domain.dto.combos.item.ItemComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValorItemComboRepository extends JpaRepository<ValorItemCombo, Long> {

    @Query("""
        SELECT new com.example.demo.domain.dtos.ItemComboDto(
            i.uuid,
            i.nome,
            COALESCE(v.valor, 0),
            v.uuid
        )
        FROM Combo c
        JOIN c.itens i
        LEFT JOIN ValorItemCombo v
          ON v.itemCombo = i
          AND v.combo = c
          AND v.estrutura = :estrutura
          AND v.competencia = :competencia
        WHERE c = :combo
    """)
    List<ItemComboDto> buscarItensDoComboComValores(
            @Param("combo") Combo combo,
            @Param("estrutura") Estrutura estrutura,
            @Param("competencia") Competencia competencia
    );
}
