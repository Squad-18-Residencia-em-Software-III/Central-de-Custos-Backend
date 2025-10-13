package com.example.demo.api.controllers.competencia;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.services.competencia.CompetenciaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/competencia")
public class CompetenciaController {

    private final CompetenciaService competenciaService;

    public CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService;
    }

    @Operation(
            summary = "Buscar todas competencias",
            description = "Endpoint utilizado para buscar todos as competencias",
            tags = "Competencia")
    @GetMapping("/buscar")
    public ResponseEntity<List<CompetenciaDto>> buscarCompetencias(
            @RequestParam(name = "status", required = false) StatusCompetencia statusCompetencia
    ){
        return ResponseEntity.ok(competenciaService.buscarCompetencias(statusCompetencia));
    }
}
