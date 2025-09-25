package com.example.demo.infra.security.jwt;

import com.example.demo.domain.entities.usuario.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class TokenJwtService {

    private final JwtEncoder jwtEncoder;

    public String generateTokenClaimSet(Authentication authentication){
        var expiresIn = 3000L; // Tempo de expiração

        Usuario usuario = (Usuario) authentication.getPrincipal();

        var claims = JwtClaimsSet.builder()
                .issuer("central-de-custos-SEDUC")
                .subject(authentication.getName())
                .claim("authorities", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .claim("nome", usuario.getNome())
                .claim("setor", usuario.getEstrutura().getNome())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
