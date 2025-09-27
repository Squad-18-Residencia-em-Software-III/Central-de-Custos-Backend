package com.example.demo.domain.validations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.solicitacoes.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SolicitacaoValidator {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;

    public SolicitacaoValidator(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository) {
        this.solicitacaoCadastroRepository = solicitacaoCadastroRepository;
    }

    public SolicitacaoCadastroUsuario validaSolicitacaoCadastroExiste(UUID solicitacaoCadastroId){
        return solicitacaoCadastroRepository.findById(solicitacaoCadastroId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação inválida ou inexistente"));
    }
    public void validaSolicitacaoCadastroExisteCpf(String cpf){
        if (solicitacaoCadastroRepository.existsByCpf(cpf)){
            throw new AccessDeniedException("Já existe uma solicitação para este CPF");
        }
    }

    public void validaSolicitacaoCadastroPendente(SolicitacaoCadastroUsuario solicitacao){
        if (!solicitacao.getStatus().equals(StatusSolicitacao.PENDENTE)){
            throw new AccessDeniedException("Solicitação não está mais PENDENTE");
        }
    }



}
