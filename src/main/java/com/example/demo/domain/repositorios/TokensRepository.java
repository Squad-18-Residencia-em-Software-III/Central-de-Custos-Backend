package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokensRepository extends JpaRepository<Tokens, Long> {

    Optional<Tokens> findByUuid(UUID uuid);

    Optional<Tokens> findByToken(String token);

    boolean existsByUsuario(Usuario usuario);
}
