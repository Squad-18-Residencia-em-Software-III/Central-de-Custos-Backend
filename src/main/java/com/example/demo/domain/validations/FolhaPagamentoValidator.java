package com.example.demo.domain.validations;

import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.repositorios.FolhaPagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FolhaPagamentoValidator {

    private final FolhaPagamentoRepository folhaPagamentoRepository;

    public FolhaPagamentoValidator(FolhaPagamentoRepository folhaPagamentoRepository) {
        this.folhaPagamentoRepository = folhaPagamentoRepository;
    }

    public FolhaPagamento validarFolhaPagamentoExiste(UUID folhaPagamentoId){
        return folhaPagamentoRepository.findByUuid(folhaPagamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Folha de pagamento inválida ou inexistente"));
    }
}
