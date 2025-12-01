package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.usuario.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByUuid(UUID uuid);

    Optional<Perfil> findByNome(String nome);
}
