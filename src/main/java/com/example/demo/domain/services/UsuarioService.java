package com.example.demo.domain.services;

import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.security.TokenDto;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.infra.security.jwt.TokenJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenJwtService tokenJwtService;

    public TokenDto login(LoginDto dto){
        var authenticationRequest = new UsernamePasswordAuthenticationToken(
                dto.cpf(),
                dto.senha()
        );

        // Esse authentication carrega o usuario autenticado
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        return new TokenDto(tokenJwtService.generateTokenClaimSet(authentication));
    }
}
