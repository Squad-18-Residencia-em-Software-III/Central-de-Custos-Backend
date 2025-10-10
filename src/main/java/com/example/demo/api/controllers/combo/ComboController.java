package com.example.demo.api.controllers.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.dto.combos.EditarComboDto;
import com.example.demo.domain.dto.combos.item.CriarItemDto;
import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.services.combo.ComboService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/combo")
public class ComboController {

    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Page<ComboDto>> buscarCombos(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "competencia", required = false) UUID competenciaId,
            @RequestParam(name = "nome", required = false) String nome,
            @PathVariable UUID estruturaId
            ){
        return ResponseEntity.ok(comboService.buscarCombos(pageNumber, competenciaId, estruturaId, nome));
    }

    @Operation(
            summary = "Criar combo",
            description = "Endpoint utilizado para criar um combo",
            tags = "Combos")
    @PostMapping
    public ResponseEntity<Void> criarCombo(@Valid @RequestBody CriarComboDto dto){
        comboService.criarCombo(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Adicionar estrutura ao combo",
            description = "Endpoint utilizado para adicionar uma estrutura um combo",
            tags = "Combos")
    @PostMapping("/new/estrutura")
    public ResponseEntity<Void> adicionarEstruturaCombo(
            @RequestParam(name = "comboId") UUID comboId,
            @RequestParam(name = "estruturaId") UUID estruturaId
            ){
        comboService.adicionarEstruturaAoCombo(comboId, estruturaId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Adicionar item ao combo",
            description = "Endpoint utilizado para adicionar um item um combo",
            tags = "Combos")
    @PostMapping("/new/item")
    public ResponseEntity<Void> adicionarItemCombo(
            @RequestParam(name = "comboId") UUID comboId,
            @RequestParam(name = "itemId") UUID itemId
    ){
        comboService.adicionarItemAoCombo(comboId, itemId);
        return ResponseEntity.ok().build();
    }

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
