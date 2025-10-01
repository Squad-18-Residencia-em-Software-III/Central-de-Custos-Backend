package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.services.SolicitacoesCadastroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cadastro")
public class SolicitacoesCadastroController {

    private final SolicitacoesCadastroService solicitacoesCadastroService;

    public SolicitacoesCadastroController(SolicitacoesCadastroService solicitacoesCadastroService) {
        this.solicitacoesCadastroService = solicitacoesCadastroService;
    }

    @Operation(
            summary = "Solicitar Cadastro",
            description = "Endpoint utilizado para enviar os dados do formulário de cadastro para as solicitações",
            tags = "Solicitações Cadastro")
    @SecurityRequirements()
    @PostMapping("/novo")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody SolicitaCadastroUsuarioDto dto){
        solicitacoesCadastroService.solicitarCadastro(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Aceitar ou Recusar Solicitação",
            description = "Endpoint utilizado para aceitar ou recusar a solicitação de cadastro, criando um token de primeiro acesso e enviando para o email",
            tags = "Solicitações Cadastro")
    @PostMapping("/solicitacao/{id}")
    public ResponseEntity<Void> aceitarOuRecusarSolicitacao(@PathVariable UUID id,
                                                            @RequestParam(name = "resposta") StatusSolicitacao statusSolicitacao){
        solicitacoesCadastroService.aprovarOuReprovarSolicitacaoCadastro(id, statusSolicitacao);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Todas as Solicitações",
            description = "Retorna todas as solicitações registradas em páginas",
            tags = "Solicitações Cadastro")
    @GetMapping("/solicitacao/all")
    public ResponseEntity<Page<CadastroUsuarioDto>> listarTodasSolicitacoes(@RequestParam(defaultValue = "1") int pagina){
        Page<CadastroUsuarioDto> solicitacoes = solicitacoesCadastroService.listarSolicitacoesCadastro(pagina);
        return ResponseEntity.ok(solicitacoes);
    }

    @Operation(
            summary = "Visualizar Solicitação",
            description = "Retorna informações da solicitação",
            tags = "Solicitações Cadastro")
    @GetMapping("/solicitacao/{id}")
    public ResponseEntity<CadastroUsuarioDto> visualizarSolicitacao(@PathVariable UUID id){
        return ResponseEntity.ok(solicitacoesCadastroService.visualizarSolicitacaoCadastro(id));
    }
}
