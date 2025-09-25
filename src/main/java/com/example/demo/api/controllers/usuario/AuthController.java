package com.example.demo.api.controllers.usuario;

import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.security.TokenDto;
import com.example.demo.domain.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }

}
