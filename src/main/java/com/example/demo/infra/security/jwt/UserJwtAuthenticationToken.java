package com.example.demo.infra.security.jwt;

import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

public class UserJwtAuthenticationToken extends JwtAuthenticationToken {
    private final Usuario usuario;

    public UserJwtAuthenticationToken(Jwt jwt,
                                      Collection<? extends GrantedAuthority> authorities,
                                      Usuario usuario) {
        super(jwt, authorities);
        this.usuario = usuario;
    }

    public Usuario getUser() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }
}
