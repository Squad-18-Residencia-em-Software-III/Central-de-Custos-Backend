package com.example.demo.infra.config.security;

import com.example.demo.infra.security.jwt.CustomJwtAuthenticationConverter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private String publicKeyPath;

    @Value("${jwt.private.key}")
    private String privateKeyPath;

    private String readKey(String path) throws Exception {
        if (path.startsWith("classpath:")) {
            String resourcePath = path.replace("classpath:", "");
            var resource = new ClassPathResource(resourcePath);
            return Files.readString(resource.getFile().toPath());
        } else {
            return Files.readString(Path.of(path));
        }
    }

    @Bean
    public RSAPublicKey loadPublicKey() throws Exception {
        String key = readKey(publicKeyPath)
                .replaceAll("-----\\w+ PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    @Bean
    public RSAPrivateKey loadPrivateKey() throws Exception {
        String key = readKey(privateKeyPath)
                .replaceAll("-----\\w+ PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey(loadPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        JWK jwk = new RSAKey.Builder(loadPublicKey()).privateKey(loadPrivateKey()).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomJwtAuthenticationConverter converter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // desativar apenas localmente
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/h2-console/**",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                    ).permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/token/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/cadastro/novo").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/cadastro/solicitacao/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.POST, "/usuario/definir-p-senha").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuario/definir-r-senha").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuario/recuperar-senha").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/estrutura/all").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/combo/buscar").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.GET, "/item/buscar").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.POST, "/item/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT, "/item/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/item/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.POST, "/combo/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PATCH, "/combo/**").hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(converter))
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // permite cookies/authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
