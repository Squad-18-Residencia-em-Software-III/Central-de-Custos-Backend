package com.example.demo.domain.validations.strategy.solicitacaocadastro.impl;

import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.validations.strategy.solicitacaocadastro.SolicitacaoCadastroValidationStrategy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class CpfJaExisteValidationImpl implements SolicitacaoCadastroValidationStrategy {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;
    private final UsuarioRepository usuarioRepository;

    public CpfJaExisteValidationImpl(SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository, UsuarioRepository usuarioRepository) {
        this.solicitacaoCadastroRepository = solicitacaoCadastroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(SolicitaCadastroUsuarioDto solicitaCadastroUsuarioDto) {
        if (!cpfExiste(solicitaCadastroUsuarioDto.cpf())){
            throw new AccessDeniedException("Já existe uma solicitação ou usuario com este CPF");
        }
    }

    private boolean cpfExiste(String cpf){
        return solicitacaoCadastroRepository.existsByCpf(cpf) || usuarioRepository.existsByCpf(cpf);
    }
}
