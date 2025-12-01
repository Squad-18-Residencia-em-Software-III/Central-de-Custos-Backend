package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>, JpaSpecificationExecutor<Municipio> {

    Optional<Municipio> findByUuid(UUID uuid);

    Optional<Municipio> findByNome(String nome);
}
