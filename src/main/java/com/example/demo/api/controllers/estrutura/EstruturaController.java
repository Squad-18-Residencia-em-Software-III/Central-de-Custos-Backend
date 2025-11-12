package com.example.demo.api.controllers.estrutura;

import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.dto.estrutura.CompetenciaAlunoEstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaInfoDto;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.services.estrutura.EstruturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/estrutura")
public class EstruturaController {

    private final EstruturaService estruturaService;

    public EstruturaController(EstruturaService estruturaService) {
        this.estruturaService = estruturaService;
    }

    @Operation(
            summary = "Buscar estruturas",
            description = "Retorna uma lista de estruturas, suporta pesquisa por nome",
            tags = "Estrutura(Setor)")
    @SecurityRequirements()
    @GetMapping("/all")
    public ResponseEntity<Page<EstruturaDto>> buscarEstruturas(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "nome", required = false
            ) String nome){
        return ResponseEntity.ok(estruturaService.buscarEstruturas(pageNumber, nome));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buscar estruturas que não possuem o combo",
            description = "Retorna uma lista de estruturas que não possuem o comboid, suporta pesquisa por nome",
            tags = "Estrutura(Setor)")
    @GetMapping("/all/n-p-combo")
    public ResponseEntity<Page<EstruturaDto>> buscarEstruturas(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "comboId") UUID comboId,
            @RequestParam(name = "nome", required = false) String nome
    ){
        return ResponseEntity.ok(estruturaService.buscarEstruturasNaoPossuemCombo(pageNumber, nome, comboId));
    }

    @Operation(
            summary = "Buscar informações da Estrutura",
            description = "Retorna informações da estrutura escolhida (Se não informar o id da estrutura, puxa a do usuario logado)",
            tags = "Estrutura(Setor)")
    @GetMapping("/info")
    public ResponseEntity<EstruturaInfoDto> buscarDetalhesEstrutura(@RequestParam(name = "estruturaId", required = false) UUID estruturaId){
        return ResponseEntity.ok(estruturaService.buscarInfoEstrutura(estruturaId));
    }

    @Operation(
            summary = "Buscar competencias que possuem combos",
            description = "Retorna competencias que possuem combos",
            tags = "Estrutura(Setor)")
    @GetMapping("/competencia/combo")
    public ResponseEntity<Page<CompetenciaDto>> buscarCompetenciasPorComboEstrutura(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "estruturaId") UUID estruturaUuid,
            @RequestParam(name = "status", required = false) StatusCompetencia statusCompetencia
            ){
        return ResponseEntity.ok(estruturaService.buscarCompetenciasPorEstrutura(pageNumber, estruturaUuid, statusCompetencia));
    }


    @Operation(
            summary = "Buscar numero de alunos por competencia",
            description = "Retorna informações do numero de alunos de uma escola pela competencia",
            tags = "Estrutura(Setor)")
    @GetMapping("/competencia-alunos")
    public ResponseEntity<CompetenciaAlunoEstruturaDto> buscarNumeroAlunosCompetencia(
            @RequestParam(name = "estruturaId") UUID estruturaUuid,
            @RequestParam(name = "competenciaId") UUID competenciaUuid) {

        return estruturaService.buscarNumeroAlunosCompetencia(estruturaUuid, competenciaUuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @Operation(
            summary = "Inserir numero de alunos",
            description = "Endpointpara inserir o numero de alunos de uma escola em uma competencia",
            tags = "Estrutura(Setor)")
    @PostMapping("/competencia-alunos")
    public ResponseEntity<Void> inserirNumeroAlunosCompetencia(@Valid @RequestBody CompetenciaAlunoEstruturaDto dto){
        estruturaService.inserirNumeroAlunosCompetencia(dto);
        return ResponseEntity.ok().build();
    }
}
