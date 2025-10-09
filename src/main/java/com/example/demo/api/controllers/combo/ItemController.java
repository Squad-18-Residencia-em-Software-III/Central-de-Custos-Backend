package com.example.demo.api.controllers.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.combos.item.*;
import com.example.demo.domain.services.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(
            summary = "Buscar Item",
            description = "Retorna informações do item",
            tags = "Itens")
    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> buscarItem(@PathVariable UUID id){
        return ResponseEntity.ok(itemService.buscarItem(id));
    }

    @Operation(
            summary = "Buscar todos os Itens",
            description = "Endpoint utilizado para fazer uma busca de todos os itens",
            tags = "Itens")
    @GetMapping("/buscar")
    public ResponseEntity<Page<ItemDto>> buscarCombos(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "nome", required = false) String nome
    ){
        return ResponseEntity.ok(itemService.buscarItens(pageNumber, nome));
    }

    @Operation(
            summary = "Buscar Itens do combo",
            description = "Retorna informações dos itens do combo",
            tags = "Itens")
    @GetMapping("/combo/{id}")
    public ResponseEntity<List<ItemComboDto>> buscarItemCombo(
            @PathVariable UUID comboUuid,
            @RequestParam(name = "estrutura") UUID estruturaUuid,
            @RequestParam(name = "competencia", required = false) UUID competenciaUuid){
        return ResponseEntity.ok(itemService.buscarItensCombo(comboUuid, estruturaUuid, competenciaUuid));
    }

    @Operation(
            summary = "Inserir valor ao item do combo",
            description = "Endpoint utilizado para inserir um valor ao determinado item do combo de uma estrutura",
            tags = "Itens")
    @PostMapping("/combo/inserirValor")
    public ResponseEntity<List<ItemComboDto>> inserirValor(@Valid @RequestBody InserirValorItemDto dto){
        itemService.inserirValor(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Criar item",
            description = "Endpoint utilizado para criar um item",
            tags = "Itens")
    @PostMapping
    public ResponseEntity<Void> criarItem(@Valid @RequestBody CriarItemDto dto){
        itemService.criarItem(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Editar Item",
            description = "Endpoint utilizado para editar um item",
            tags = "Itens")
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarItem(@PathVariable UUID id, @RequestBody EditarItemDto dto){
        itemService.editarItem(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Deletar Item",
            description = "Endpoint utilizado para deletar um item",
            tags = "Itens")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable UUID id){
        itemService.deletarItem(id);
        return ResponseEntity.ok().build();
    }
}
