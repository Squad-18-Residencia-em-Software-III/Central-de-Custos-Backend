package com.example.demo.api.controllers.relatorio;

import com.example.demo.domain.dto.relatorios.HeaderPainelSetorDto;
import com.example.demo.domain.dto.relatorios.escola.CustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.escola.ListaCustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.services.relatorios.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @Operation(
            summary = "GRÁFICO: Buscar gastos totais por competencia e ano",
            description = "Retorna dados para gerar grafico de gastos totais separados por competencia e ano",
            tags = "Relatórios")
    @GetMapping("/grafico/gastos-t-competencia")
    public ResponseEntity<List<GastosTotaisCompetenciaDto>> buscarGastosPorCompetencia(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "ano") int ano
    ) {
        return ResponseEntity.ok(relatorioService.buscarGatosTotaisPorCompetencia(estruturaId, ano));
    }

    @Operation(
            summary = "RELATÓRIO: Buscar custos por aluno",
            description = "Retorna dados dos custos por aluno",
            tags = "Relatórios")
    @GetMapping("/relatorio/custo-p-aluno")
    public ResponseEntity<List<CustoPorAlunoDto>> buscarCustosPorAluno(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "ano") int ano
    ) {
        return ResponseEntity.ok(relatorioService.buscarCustoPorAluno(estruturaId, ano));
    }

    @Operation(
            summary = "RELATÓRIO: Buscar custos por aluno das escolas da DIRETORIA",
            description = "Retorna dados dos custos por aluno",
            tags = "Relatórios")
    @GetMapping("/relatorio/custo-p-aluno/diretoria")
    public ResponseEntity<List<ListaCustoPorAlunoDto>> buscarCustosPorAluno(
            @RequestParam(name = "estruturaId") UUID diretoriaId,
            @RequestParam(name = "ano") int ano,
            @RequestParam(name = "mes") int mes
    ) {
        return ResponseEntity.ok(relatorioService.buscarCustoPorAlunoPorDiretoria(diretoriaId, ano, mes));
    }

    @Operation(
            summary = "DASHBOARD: Exibir dados do setor",
            description = "Retorna dados dosetor para o header do dashboard",
            tags = "Relatórios")
    @GetMapping("/dashboard/header")
    public ResponseEntity<HeaderPainelSetorDto> getHeader(
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId
    ) {
        return ResponseEntity.ok(relatorioService.exibirDadosHeaderPainelSetor(estruturaId));
    }
}
