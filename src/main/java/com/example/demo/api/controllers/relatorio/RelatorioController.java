package com.example.demo.api.controllers.relatorio;

import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.services.relatorios.RelatorioService;
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

    @GetMapping("/grafico/gastos-t-competencia")
    public List<GastosTotaisCompetenciaDto> buscarGastosPorCompetencia(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "ano") int ano
    ) {
        return relatorioService.buscarGatosTotaisPorCompetencia(estruturaId, ano);
    }
}
