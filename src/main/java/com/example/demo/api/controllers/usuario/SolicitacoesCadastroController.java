package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.services.SolicitacoesCadastroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class SolicitacoesCadastroController {

    private final SolicitacoesCadastroService solicitacoesCadastroService;

    public SolicitacoesCadastroController(SolicitacoesCadastroService solicitacoesCadastroService) {
        this.solicitacoesCadastroService = solicitacoesCadastroService;
    }

    @PostMapping("/novo")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody CadastroUsuarioDto dto){
        solicitacoesCadastroService.solicitarCadastro(dto);
        return ResponseEntity.ok().build();
    }
}
