package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByUuid(UUID uuid);

    Optional<Usuario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

}
