package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComboRepository extends JpaRepository<Combo, Long>, JpaSpecificationExecutor<Combo> {
    Optional<Combo> findByUuid(UUID comboId);

    List<Combo> findAllByItensContaining(ItemCombo itemCombo);
}
