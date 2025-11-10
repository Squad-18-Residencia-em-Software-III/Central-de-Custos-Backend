package com.example.demo.domain.services.relatorios;

import com.example.demo.domain.dto.relatorios.HeaderPainelSetorDto;
import com.example.demo.domain.dto.relatorios.escola.CustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.validations.EstruturaValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RelatorioService {

    private final ValorItemComboRepository valorItemComboRepository;
    private final EstruturaValidator estruturaValidator;
    private final CompetenciaRepository competenciaRepository;

    public RelatorioService(ValorItemComboRepository valorItemComboRepository, EstruturaValidator estruturaValidator, CompetenciaRepository competenciaRepository) {
        this.valorItemComboRepository = valorItemComboRepository;
        this.estruturaValidator = estruturaValidator;
        this.competenciaRepository = competenciaRepository;
    }

    public List<GastosTotaisCompetenciaDto> buscarGatosTotaisPorCompetencia(UUID estruturaId, int ano){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        List<Object[]> resultados = valorItemComboRepository.gastosTotaisPorCompetenciaAno(estrutura.getId(), ano);

        return resultados.stream()
                .map(obj -> new GastosTotaisCompetenciaDto(
                        (String) obj[0],
                        (BigDecimal) obj[1]
                ))
                .toList();
    }

    public List<CustoPorAlunoDto> buscarCustoPorAluno(UUID estruturaId, int ano){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        estruturaValidator.validaClassificacaoEstrutura(estrutura, ClassificacaoEstrutura.ESCOLA);
        List<Object[]> resultados = valorItemComboRepository.custosPorAluno(estrutura.getId(), ano);

        return resultados.stream()
                .map(obj -> new CustoPorAlunoDto(
                        (String) obj[0],
                        (BigDecimal) obj[1],
                        (Integer) obj[2],
                        (BigDecimal) obj[3]
                ))
                .toList();
    }

    public HeaderPainelSetorDto exibirDadosHeaderPainelSetor(UUID estruturaId){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);

        Object[] resultado;

        switch (estrutura.getClassificacaoEstrutura()){
            case SECRETARIA -> resultado = (Object[]) competenciaRepository.findHeaderForSecretaria();
            case DIRETORIA -> resultado = (Object[]) competenciaRepository.findHeaderForDiretoria(estrutura.getId());
            default -> throw new IllegalArgumentException("Tipo de estrutura inválido");
        }

        return new HeaderPainelSetorDto(
                resultado[0] != null ? ((java.sql.Date) resultado[0]).toLocalDate() : null,
                resultado[1] != null ? ((Number) resultado[1]).intValue() : null, // quantidade_setores
                resultado[3] != null ? ((Number) resultado[3]).intValue() : null, // quantidade_escolas
                resultado[2] != null ? ((Number) resultado[2]).intValue() : null, // quantidade_alunos
                resultado[4] != null ? (BigDecimal) resultado[4] : BigDecimal.ZERO // valor_total_competencia
        );
    }

}
