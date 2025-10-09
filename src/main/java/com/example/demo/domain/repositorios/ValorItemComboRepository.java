package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.combos.ValorItemCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ValorItemComboRepository extends JpaRepository<ValorItemCombo, Long>, JpaSpecificationExecutor<ValorItemCombo> {
}
