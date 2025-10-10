package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.estrutura.Estrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EstruturaRepository extends JpaRepository<Estrutura, Long> {

    Optional<Estrutura> findByUuid(UUID uuid);

    Optional<Estrutura> findByNome(String nome);
}
