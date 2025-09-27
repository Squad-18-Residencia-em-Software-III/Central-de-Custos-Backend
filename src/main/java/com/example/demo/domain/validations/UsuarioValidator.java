package com.example.demo.domain.validations;

import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.PerfilRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    public Perfil validaPerfilExisteNome(String nomePerfil){
        return perfilRepository.findByNome(nomePerfil)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Perfil %s não encontrado", nomePerfil)));
    }

    public void validaUsuarioExisteCpf(String cpf){
        if (!usuarioRepository.existsByCpf(cpf)){
            throw new EntityNotFoundException("Cpf inválido ou não cadastrado.");
        }
    }

    public Usuario validaUsuarioCpf(String cpf){
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Cpf inválido ou não cadastrado"));
    }
}
