package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstruturaRepository extends JpaRepository<Estrutura, Long> {

    Optional<Estrutura> findByUuid(UUID uuid);

    Optional<Estrutura> findByNome(String nome);
}
