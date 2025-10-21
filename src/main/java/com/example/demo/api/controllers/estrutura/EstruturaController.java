package com.example.demo.api.controllers.estrutura;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaInfoDto;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.services.estrutura.EstruturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<EstruturaDto>> buscarEstruturas(@RequestParam(name = "nome", required = false) String nome){
        return ResponseEntity.ok(estruturaService.buscarEstruturas(nome));
    }

    @Operation(
            summary = "Buscar Sub Setores",
            description = "Retorna uma lista de subsetores (estruturas filhas) de uma estrutura. (Se não informar o id da estrutura, puxa a do usuario logado)",
            tags = "Estrutura(Setor)")
    @GetMapping("/buscar-subsetores")
    public ResponseEntity<Page<EstruturaDto>> buscarSubSetores(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "classificacao", required = false) ClassificacaoEstrutura classificacaoEstrutura
    ){
        return ResponseEntity.ok(estruturaService.buscarSubSetores(pageNumber, estruturaId, nome, classificacaoEstrutura));
    }

    @Operation(
            summary = "Buscar informações da Estrutura",
            description = "Retorna informações da estrutura escolhida (Se não informar o id da estrutura, puxa a do usuario logado)",
            tags = "Estrutura(Setor)")
    @GetMapping("/info")
    public ResponseEntity<EstruturaInfoDto> buscarDetalhesEstrutura(@RequestParam(name = "estruturaId", required = false) UUID estruturaId){
        return ResponseEntity.ok(estruturaService.buscarInfoEstrutura(estruturaId));
    }
}
