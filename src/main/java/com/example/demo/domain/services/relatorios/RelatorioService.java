package com.example.demo.domain.services.relatorios;

import com.example.demo.domain.dto.relatorios.HeaderPainelSetorDto;
import com.example.demo.domain.dto.relatorios.escola.CustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.escola.ListaCustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RelatorioService {

    private final ValorItemComboRepository valorItemComboRepository;
    private final EstruturaValidator estruturaValidator;
    private final ComboValidator comboValidator;
    private final CompetenciaRepository competenciaRepository;

    public RelatorioService(ValorItemComboRepository valorItemComboRepository, EstruturaValidator estruturaValidator, ComboValidator comboValidator, CompetenciaRepository competenciaRepository) {
        this.valorItemComboRepository = valorItemComboRepository;
        this.estruturaValidator = estruturaValidator;
        this.comboValidator = comboValidator;
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

    public List<ListaCustoPorAlunoDto> buscarCustoPorAlunoPorDiretoria(UUID estruturaId, int ano, int mes) {
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);

        List<Object[]> resultados = valorItemComboRepository.findEscolasComCustoPorAluno(estrutura.getId(),ano, mes);

        return resultados.stream()
                .map(obj -> new ListaCustoPorAlunoDto(
                        ((Number) obj[0]).longValue(),   // id_escola
                        (String) obj[1],                 // nome_escola
                        (BigDecimal) obj[2],             // total_valor
                        ((Number) obj[3]).intValue(),    // numero_alunos
                        (BigDecimal) obj[4]              // custo_aluno
                ))
                .toList();
    }

    public HeaderPainelSetorDto exibirDadosHeaderPainelSetor(UUID estruturaId, UUID competenciaId){
        Competencia competencia = comboValidator.validarCompetenciaExiste(competenciaId);

        Estrutura estrutura;
        if (estruturaId != null){
            estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        } else {
            Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();
            estrutura = usuario.getEstrutura();
        }

        Object[] resultado;

        switch (estrutura.getClassificacaoEstrutura()){
            case SECRETARIA -> resultado = (Object[]) competenciaRepository.findHeaderForSecretaria(competencia.getId());
            case DIRETORIA -> resultado = (Object[]) competenciaRepository.findHeaderForDiretoria(estrutura.getId(), competencia.getId());
            case ESCOLA -> resultado = (Object[]) competenciaRepository.findHeaderForEscola(estrutura.getId(), competencia.getId());
            default -> throw new IllegalArgumentException("Tipo de estrutura inválido");
        }

        if (resultado == null) {
            return new HeaderPainelSetorDto(
                    LocalDate.now(),
                    0,
                    0,
                    0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        return new HeaderPainelSetorDto(
                resultado[0] != null ? ((java.sql.Date) resultado[0]).toLocalDate() : null,
                resultado[1] != null ? ((Number) resultado[1]).intValue() : null, // quantidade_setores
                resultado[2] != null ? ((Number) resultado[2]).intValue() : null, // quantidade_escolas
                resultado[3] != null ? ((Number) resultado[3]).intValue() : null, // quantidade_alunos
                resultado[4] != null ? (BigDecimal) resultado[4] : BigDecimal.ZERO, // valor_total_competencia
                resultado[5] != null ? (BigDecimal) resultado[5] : BigDecimal.ZERO // custo_por_aluno
        );
    }

}
