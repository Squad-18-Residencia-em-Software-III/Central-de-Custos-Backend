package com.example.demo.infra.security;

import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) {
        Usuario user =  usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if(user.isPrimeiroAcesso()){
            throw new BadCredentialsException("Seu usuário não definiu a senha de primeiro acesso");
        }

        return user;
    }
}
