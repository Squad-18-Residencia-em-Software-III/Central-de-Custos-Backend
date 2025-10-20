package com.example.demo.api.controllers.solicitacoes;

import com.example.demo.domain.dto.solicitacoes.InfoSolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.dto.solicitacoes.RespostaSolicitacaoInterna;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.SolicitacaoInternaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacoesInternasController {

    private final SolicitacaoInternaService solicitacaoInternaService;

    public SolicitacoesInternasController(SolicitacaoInternaService solicitacaoInternaService) {
        this.solicitacaoInternaService = solicitacaoInternaService;
    }

    @Operation(
            summary = "Nova Solicitação",
            description = "Endpoint utilizado para fazer uma nova solicitacao",
            tags = "Solicitações Internas")
    @PostMapping("/nova")
    public ResponseEntity<Void> novaSolicitacao(@Valid @RequestBody NovaSolicitacaoInternaDto dto){
        solicitacaoInternaService.novaSolicitacao(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Definir Status Solicitacao (ACEITAR OU RECUSAR)",
            description = "Endpoint utilizado aceitar ou recusar uma solicitacao interna",
            tags = "Solicitações Internas")
    @PostMapping("/definir-status")
    public ResponseEntity<Void> definirStatusSolicitacao(
            @RequestParam(name = "solicitacaoInternaId") Long solicitacaoInternaId,
            @RequestParam(name = "status") StatusSolicitacao statusSolicitacao,
            @RequestBody RespostaSolicitacaoInterna resposta
            ){
        solicitacaoInternaService.definirStatusSolicitacao(solicitacaoInternaId, statusSolicitacao, resposta);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Buscar Detalhes da Solicitacao",
            description = "Retorna os dados da solicitação",
            tags = "Solicitações Internas")
    @GetMapping("/{id}")
    public ResponseEntity<InfoSolicitacaoInternaDto> buscarDetalhesSolicitacao(@PathVariable Long id){
        return ResponseEntity.ok(solicitacaoInternaService.buscarDetalhesSolicitacao(id));
    }
}
