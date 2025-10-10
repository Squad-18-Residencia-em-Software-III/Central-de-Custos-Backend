package com.example.demo.api.controllers.estrutura;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.services.estrutura.EstruturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
