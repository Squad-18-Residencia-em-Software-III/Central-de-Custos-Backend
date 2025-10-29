package com.example.demo.api.controllers.competencia;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.services.competencia.CompetenciaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar Competencia",
            description = "Endpoint utilizado para criar uma nova competencia",
            tags = "Competencia")
    @PostMapping
    public ResponseEntity<Void> criarCompetencia(
            @RequestParam(name = "data")LocalDate localDate
            ){
        competenciaService.criarCompetencia(localDate);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Definir status da ompetencia",
            description = "Endpoint utilizado para definir o status da competencia",
            tags = "Competencia")
    @PatchMapping("/definir-status")
    public ResponseEntity<Void> definirStatusCompetencia(
            @RequestParam(name = "competenciaId")UUID competenciaId,
            @RequestParam(name = "status") StatusCompetencia statusCompetencia
            ){
        competenciaService.definirStatusCompetencia(competenciaId, statusCompetencia);
        return ResponseEntity.ok().build();
    }
}
