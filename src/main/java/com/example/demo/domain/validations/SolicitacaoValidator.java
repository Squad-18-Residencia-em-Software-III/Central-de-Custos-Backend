package com.example.demo.domain.validations;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SolicitacaoValidator {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoValidator(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository, UsuarioRepository usuarioRepository) {
        this.solicitacaoCadastroRepository = solicitacaoCadastroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public SolicitacaoCadastroUsuario validaSolicitacaoCadastroExiste(UUID solicitacaoCadastroId){
        return solicitacaoCadastroRepository.findByUuid(solicitacaoCadastroId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação inválida ou inexistente"));
    }
    public void validaSolicitacaoCadastroExisteCpf(String cpf){
        if (solicitacaoCadastroRepository.existsByCpf(cpf) || usuarioRepository.existsByCpf(cpf)){
            throw new AccessDeniedException("Já existe uma solicitação ou usuario com este CPF");
        }
    }

    public void validaSolicitacaoCadastroPendente(SolicitacaoCadastroUsuario solicitacao){
        if (!solicitacao.getStatus().equals(StatusSolicitacao.PENDENTE)){
            throw new AccessDeniedException("Solicitação não está mais PENDENTE");
        }
    }



}
