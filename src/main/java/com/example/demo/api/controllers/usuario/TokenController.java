package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.usuario.CpfDto;
import com.example.demo.domain.entities.solicitacoes.TipoToken;
import com.example.demo.domain.services.TokensService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokensService tokensService;

    public TokenController(TokensService tokensService) {
        this.tokensService = tokensService;
    }

    @Operation(
            summary = "Validar Token",
            description = "Endpoint para validar se o token está válido, para assim poder definir a primeira senha ou recuperar",
            tags = "Tokens")
    @SecurityRequirements()
    @PostMapping("/validar")
    public ResponseEntity<Void> validarTokenPrimeiroAcesso(@RequestParam(name = "token") String token,
                                                           @RequestParam(name = "tipo") TipoToken tipoToken,
                                                           @Valid @RequestBody CpfDto cpfDto){
        tokensService.validarToken(token, cpfDto.cpf(), tipoToken);
        return ResponseEntity.ok().build();
    }
}
