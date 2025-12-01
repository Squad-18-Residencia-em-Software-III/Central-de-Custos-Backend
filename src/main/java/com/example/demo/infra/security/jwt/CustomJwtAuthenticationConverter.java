package com.example.demo.infra.security.jwt;

import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.infra.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        Usuario user = (Usuario) userDetailsService.loadUserByUsername(username);

        return new UserJwtAuthenticationToken(jwt, user.getAuthorities(), user);
    }
}
