package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.combos.ItemCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;


public interface ItemRepository extends JpaRepository<ItemCombo, Long>, JpaSpecificationExecutor<ItemCombo> {
    boolean existsByNome(String nome);

    Optional<ItemCombo> findByUuid(UUID itemId);
}
