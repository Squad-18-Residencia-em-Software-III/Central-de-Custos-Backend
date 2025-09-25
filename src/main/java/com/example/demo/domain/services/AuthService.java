package com.example.demo.domain.services;

import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.security.TokenDto;
import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.MunicipioRepository;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.infra.security.jwt.TokenJwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
