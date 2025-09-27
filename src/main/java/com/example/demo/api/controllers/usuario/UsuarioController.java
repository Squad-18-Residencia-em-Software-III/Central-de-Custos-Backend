package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Definir Primeira Senha",
            description = "Endpoint para definir a primeira senha do usuario, retorna um token de login do usuario",
            tags = "Usuario")
    @SecurityRequirements()
    @PostMapping("/definir-p-senha")
    public ResponseEntity<AccessTokenDto> definirPrimeiraSenha(@RequestParam(name = "token") String token,
                                                               @RequestParam(name = "cpf") String cpf,
                                                               @Valid @RequestBody NovaSenhaDto dto) {
        return ResponseEntity.ok(usuarioService.defineNovaSenhaUsuario(token, cpf, dto));
    }
}
