package com.example.demo.domain.services.folhapagamento;

import com.example.demo.domain.dto.folhaPagamento.FolhaPagamentoDto;
import com.example.demo.domain.dto.folhaPagamento.InserirFolhaPagamentoDto;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.mapper.FolhaPagamentoMapper;
import com.example.demo.domain.repositorios.FolhaPagamentoRepository;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FolhaPagamentoService {

    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final EstruturaValidator estruturaValidator;
    private final ComboValidator comboValidator;
    private final FolhaPagamentoMapper folhaPagamentoMapper;

    public FolhaPagamentoService(FolhaPagamentoRepository folhaPagamentoRepository, EstruturaValidator estruturaValidator, ComboValidator comboValidator, FolhaPagamentoMapper folhaPagamentoMapper) {
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.estruturaValidator = estruturaValidator;
        this.comboValidator = comboValidator;
        this.folhaPagamentoMapper = folhaPagamentoMapper;
    }

    public FolhaPagamentoDto buscarFolhaPagamento(UUID estruturaId, UUID competenciaId){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        Competencia competencia = comboValidator.validarCompetenciaExiste(competenciaId);

        Optional<FolhaPagamento> optionalFolhaPagamento = folhaPagamentoRepository.findByEstruturaAndCompetencia(estrutura, competencia);
        FolhaPagamento folhaPagamento;
        if (optionalFolhaPagamento.isEmpty()){
            return null;
        } else {
            folhaPagamento = optionalFolhaPagamento.get();
        }

        return folhaPagamentoMapper.toDto(folhaPagamento);
    }

    @Transactional
    public void inserirValorFolhaPagamento(InserirFolhaPagamentoDto dto){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());
        Competencia competencia = comboValidator.validarCompetenciaExiste(dto.competenciaId());

        comboValidator.validarCompetenciaAberta(competencia);
        Optional<FolhaPagamento>  optionalFolhaPagamento = folhaPagamentoRepository.findByEstruturaAndCompetencia(estrutura, competencia);
        FolhaPagamento folhaDePagamento;
        if (optionalFolhaPagamento.isEmpty()){
            folhaDePagamento = new FolhaPagamento();
            folhaDePagamento.setEstrutura(estrutura);
            folhaDePagamento.setCompetencia(competencia);
            folhaDePagamento.setValor(dto.valor());
        } else {
            folhaDePagamento = optionalFolhaPagamento.get();
            folhaDePagamento.setValor(dto.valor());
        }

        folhaPagamentoRepository.save(folhaDePagamento);
    }

}
