package com.example.demo.api.controllers.folhaPagamento;

import com.example.demo.domain.dto.folhaPagamento.FolhaPagamentoDto;
import com.example.demo.domain.dto.folhaPagamento.InserirFolhaPagamentoDto;
import com.example.demo.domain.services.folhapagamento.FolhaPagamentoService;
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

    @GetMapping("/buscar")
    public ResponseEntity<FolhaPagamentoDto> buscarFolhaPagamento(
            @RequestParam(name = "estruturaId") UUID estruturaId,
            @RequestParam(name = "competenciaId") UUID competenciaId
    ){
        FolhaPagamentoDto folhaPagamento = folhaPagamentoService.buscarFolhaPagamento(estruturaId, competenciaId);
        if (folhaPagamento == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(folhaPagamento);
    }

    @PostMapping("/inserir-valor")
    public ResponseEntity<Void> inserirValorFolhaPagamento(@Valid @RequestBody InserirFolhaPagamentoDto dto){
        folhaPagamentoService.inserirValorFolhaPagamento(dto);
        return ResponseEntity.ok().build();
    }
}
