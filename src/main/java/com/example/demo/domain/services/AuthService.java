package com.example.demo.domain.services;

import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.security.TokenDto;
import com.example.demo.infra.security.jwt.TokenJwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenJwtService tokenJwtService;

    public AuthService(AuthenticationManager authenticationManager, TokenJwtService tokenJwtService) {
        this.authenticationManager = authenticationManager;
        this.tokenJwtService = tokenJwtService;
    }

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
