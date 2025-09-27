package com.example.demo.domain.repositorios;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SolicitacaoCadastroUsuarioRepository extends JpaRepository<SolicitacaoCadastroUsuario, UUID> {
    boolean existsByCpf(@NotBlank @CPF String cpf);
}
