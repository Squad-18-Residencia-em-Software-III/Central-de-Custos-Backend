package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.usuario.CpfDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.dto.usuario.UsuarioDto;
import com.example.demo.domain.dto.usuario.UsuarioInfoDto;
import com.example.demo.domain.services.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(
            summary = "Recuperar Senha",
            description = "Endpoint para solicitar uma recuperação de senha",
            tags = "Usuario")
    @SecurityRequirements()
    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> solicitaRecuperarSenha(@Valid @RequestBody CpfDto cpf) {
        usuarioService.solicitaRecuperarSenha(cpf.cpf());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Redefinir Senha Recuperar",
            description = "Endpoint para redefinir a senha recuperada do usuario",
            tags = "Usuario")
    @SecurityRequirements()
    @PostMapping("/definir-r-senha")
    public ResponseEntity<AccessTokenDto> definirRecuperarSenha(@RequestParam(name = "token") String token,
                                                               @RequestParam(name = "cpf") String cpf,
                                                               @Valid @RequestBody NovaSenhaDto dto) {
        usuarioService.defineNovaSenhaUsuario(token, cpf, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Visualizar Informações do Usuário",
            description = "Retorna informações do usuário por uuid ou cpf. Usuários não-admin só podem ver seu próprio perfil.",
            tags = "Usuario")
    @GetMapping("/info")
    public ResponseEntity<UsuarioInfoDto> visualizarInfoUsuario(
            @RequestParam(name = "usuarioId", required = false) UUID uuid){
        return ResponseEntity.ok(usuarioService.visualizarInfoUsuario(uuid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buscar Usuarios",
            description = "Retorna uma lista de usuarios",
            tags = "Usuario")
    @GetMapping("/all")
    public ResponseEntity<Page<UsuarioDto>> buscarUsuarios(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "estruturaId", required = false) UUID estruturaId,
            @RequestParam(name = "primeiroAcesso", required = false) Boolean primeiroAcesso
            ){
        return ResponseEntity.ok(usuarioService.buscarUsuarios(pageNumber, nome, estruturaId, primeiroAcesso));
    }
}
