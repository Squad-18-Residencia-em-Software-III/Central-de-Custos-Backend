package com.example.demo.api.controllers.relatorio;

import com.example.demo.domain.dto.relatorios.HeaderPainelSetorDto;
import com.example.demo.domain.dto.relatorios.escola.CustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.escola.ListaCustoPorAlunoDto;
import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.dto.relatorios.graficos.ItensMaisCarosEstruturaDto;
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
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "competenciaId", required = false) UUID competenciaId
    ) {
        return ResponseEntity.ok(relatorioService.buscarGatosTotaisPorCompetencia(estruturaId, competenciaId));
    }

    @Operation(
            summary = "RELATÓRIO: Buscar custos por aluno",
            description = "Retorna dados dos custos por aluno",
            tags = "Relatórios")
    @GetMapping("/relatorio/custo-p-aluno")
    public ResponseEntity<List<CustoPorAlunoDto>> buscarCustosPorAluno(
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId
    ) {
        return ResponseEntity.ok(relatorioService.buscarCustoPorAluno(estruturaId));
    }

    @Operation(
            summary = "RELATÓRIO: Buscar custos por aluno das escolas da DIRETORIA",
            description = "Retorna dados dos custos por aluno",
            tags = "Relatórios")
    @GetMapping("/relatorio/custo-p-aluno/diretoria")
    public ResponseEntity<List<ListaCustoPorAlunoDto>> buscarCustosPorAlunoDiretoria(
            @RequestParam(name = "estruturaId", required = false) UUID diretoriaId,
            @RequestParam(name = "competenciaId", required = false) UUID competenciaId
    ) {
        return ResponseEntity.ok(relatorioService.buscarCustoPorAlunoPorDiretoria(diretoriaId, competenciaId));
    }

    @Operation(
            summary = "DASHBOARD: Exibir dados do setor",
            description = "Retorna dados dosetor para o header do dashboard",
            tags = "Relatórios")
    @GetMapping("/dashboard/header")
    public ResponseEntity<HeaderPainelSetorDto> getHeader(
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "competenciaId", required = false) UUID competenciaId
    ) {
        return ResponseEntity.ok(relatorioService.exibirDadosHeaderPainelSetor(estruturaId, competenciaId));
    }

    @Operation(
            summary = "PIZZA: Exibir itens mais caros do setor",
            description = "Retorna iuma lista de itens mais caros do setor",
            tags = "Relatórios")
    @GetMapping("/pizza/itens/estrutura")
    public ResponseEntity<List<ItensMaisCarosEstruturaDto>> buscarItensMaisCaros(
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "competenciaId", required = false) UUID competenciaId
    ) {
        return ResponseEntity.ok(relatorioService.buscarItensMaisCarosEstrutura(estruturaId, competenciaId));
    }

    @Operation(
            summary = "PIZZA: Exibir itens mais caros dos setores filhos",
            description = "Retorna iuma lista de itens mais caros dos setores filhos desse setor",
            tags = "Relatórios")
    @GetMapping("/pizza/itens/estrutura/filhos")
    public ResponseEntity<List<ItensMaisCarosEstruturaDto>> buscarItensMaisCarosFilhos(
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "competenciaId", required = false) UUID competenciaId
    ) {
        return ResponseEntity.ok(relatorioService.buscarItensMaisCarosEstruturaFilhos(estruturaId, competenciaId));
    }
}
