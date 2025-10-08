package com.example.demo.api.controllers.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.services.combo.ComboService;
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
}
