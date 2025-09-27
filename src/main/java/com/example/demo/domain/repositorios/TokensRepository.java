package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.solicitacoes.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokensRepository extends JpaRepository<Tokens, UUID> {
    Optional<Tokens> findByToken(String token);
}
