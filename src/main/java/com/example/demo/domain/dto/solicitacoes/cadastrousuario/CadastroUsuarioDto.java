package com.example.demo.domain.dto.solicitacoes.cadastrousuario;

import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CadastroUsuarioDto(
        UUID id,
        String nome,
        LocalDateTime criadoEm,
        StatusSolicitacao status
) {}

