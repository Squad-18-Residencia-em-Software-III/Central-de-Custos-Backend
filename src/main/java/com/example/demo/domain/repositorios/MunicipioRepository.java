package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

    Optional<Municipio> findByUuid(UUID uuid);

    Optional<Municipio> findByNome(String nome);
}
