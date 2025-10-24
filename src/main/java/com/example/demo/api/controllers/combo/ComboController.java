package com.example.demo.api.controllers.combo;

import com.example.demo.domain.dto.combos.*;
import com.example.demo.domain.services.combo.ComboService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/combo")
public class ComboController {

    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @Operation(
            summary = "Buscar combo do setor",
            description = "Endpoint utilizado para buscar combos de um setor",
            tags = "Combos")
    @GetMapping("/buscar/estrutura")
    public ResponseEntity<List<ComboDto>> buscarCombosEstrutura(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "nome", required = false) String nome
            ){
        return ResponseEntity.ok(comboService.buscarCombosEstrutura(estruturaId, nome));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buscar todos combos",
            description = "Endpoint utilizado para buscar todos os combos",
            tags = "Combos")
    @GetMapping("/buscar")
    public ResponseEntity<Page<ComboDto>> buscarCombos(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "nome", required = false) String nome
    ){
        return ResponseEntity.ok(comboService.buscarCombos(pageNumber, nome));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Visualizar combo",
            description = "Endpoint utilizado para visualizar detalhes de um combo",
            tags = "Combos")
    @GetMapping("/{id}")
    public ResponseEntity<ComboDetalhadoDto> visualizarCombo(@PathVariable UUID id){
        return ResponseEntity.ok(comboService.buscarComboInfo(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar combo",
            description = "Endpoint utilizado para criar um combo",
            tags = "Combos")
    @PostMapping
    public ResponseEntity<Void> criarCombo(@Valid @RequestBody CriarComboDto dto){
        comboService.criarCombo(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Adicionar estrutura ao combo",
            description = "Endpoint utilizado para adicionar uma estrutura um combo",
            tags = "Combos")
    @PostMapping("/novo/estrutura/{id}")
    public ResponseEntity<Void> adicionarEstruturaCombo(
            @PathVariable UUID id,
            @Valid @RequestBody InclusaoDto dto
            ){
        comboService.adicionarEstruturasAoCombo(id, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Adicionar item ao combo",
            description = "Endpoint utilizado para adicionar um item um combo",
            tags = "Combos")
    @PostMapping("/novo/item/{id}")
    public ResponseEntity<Void> adicionarItemCombo(
            @PathVariable UUID id,
            @Valid @RequestBody InclusaoDto dto
    ){
        comboService.adicionarItensAoCombo(id, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Remover estrutura do combo",
            description = "Endpoint utilizado para remover uma estrutura de um combo",
            tags = "Combos")
    @PatchMapping("/remove/estrutura")
    public ResponseEntity<Void> removerEstruturaCombo(
            @RequestParam(name = "comboId") UUID comboId,
            @RequestParam(name = "estruturaId") UUID estruturaId
    ){
        comboService.removerEstruturaDoCombo(comboId, estruturaId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Remover item do combo",
            description = "Endpoint utilizado para remover um item de um combo",
            tags = "Combos")
    @PatchMapping("/remove/item")
    public ResponseEntity<Void> removerItemCombo(
            @RequestParam(name = "comboId") UUID comboId,
            @RequestParam(name = "itemId") UUID itemId
    ){
        comboService.removerItemDoCombo(comboId, itemId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Editar combo",
            description = "Endpoint utilizado para editar um combo",
            tags = "Combos")
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarCombo(@PathVariable UUID id, @RequestBody EditarComboDto dto){
        comboService.editarCombo(id, dto);
        return ResponseEntity.ok().build();
    }
}
