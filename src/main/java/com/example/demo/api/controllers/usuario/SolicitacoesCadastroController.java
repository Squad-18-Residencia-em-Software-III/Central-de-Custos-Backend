package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.services.SolicitacoesCadastroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastro")
public class SolicitacoesCadastroController {

    private final SolicitacoesCadastroService solicitacoesCadastroService;

    public SolicitacoesCadastroController(SolicitacoesCadastroService solicitacoesCadastroService) {
        this.solicitacoesCadastroService = solicitacoesCadastroService;
    }

    @PostMapping("/novo")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody SolicitaCadastroUsuarioDto dto){
        solicitacoesCadastroService.solicitarCadastro(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/solicitacao/all")
    public ResponseEntity<Page<CadastroUsuarioDto>> listarTodasSolicitacoes(@RequestParam(defaultValue = "1") int pagina){
        Page<CadastroUsuarioDto> solicitacoes = solicitacoesCadastroService.listarSolicitacoesCadastro(pagina);
        return ResponseEntity.ok(solicitacoes);
    }
}
