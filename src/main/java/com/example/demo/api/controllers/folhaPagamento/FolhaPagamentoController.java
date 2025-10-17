package com.example.demo.api.controllers.folhaPagamento;

import com.example.demo.domain.dto.folhaPagamento.FolhaPagamentoDto;
import com.example.demo.domain.dto.folhaPagamento.InserirFolhaPagamentoDto;
import com.example.demo.domain.services.folhapagamento.FolhaPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/folha-pagamento")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }

    @Operation(
            summary = "Buscar folha de Pagamento",
            description = "Retorna a folha de pagamento da estrutura na competencia informada",
            tags = "Folha de Pagamento (RH)")
    @GetMapping("/buscar")
    public ResponseEntity<FolhaPagamentoDto> buscarFolhaPagamento(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "competenciaId") UUID competenciaId
    ){
        FolhaPagamentoDto folhaPagamento = folhaPagamentoService.buscarFolhaPagamento(estruturaId, competenciaId);
        if (folhaPagamento == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(folhaPagamento);
    }

    @Operation(
            summary = "Inserir valor na folha de Pagamento",
            description = "Endpoint responsável por inserir valor na folha de pagamento",
            tags = "Folha de Pagamento (RH)")
    @PostMapping("/inserir-valor")
    public ResponseEntity<Void> inserirValorFolhaPagamento(@Valid @RequestBody InserirFolhaPagamentoDto dto){
        folhaPagamentoService.inserirValorFolhaPagamento(dto);
        return ResponseEntity.ok().build();
    }
}
