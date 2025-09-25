package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

}
