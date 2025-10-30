package com.example.demo.domain.validations;

import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.PerfilRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public Usuario validaUsuarioExisteUuid(UUID uuid) {
        return usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Uuid inválido ou não cadastrado."));
    }

    public Usuario validaUsuarioExisteCpf(String cpf){
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Cpf inválido ou não cadastrado."));
    }

    public Usuario validaUsuarioCpf(String cpf){
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Cpf inválido ou não cadastrado"));
    }

    public void validaUsuarioPrimeiroAcesso(Usuario usuario){
        if (usuario.isPrimeiroAcesso()){
            throw new AccessDeniedException("O usuário precisa realizar o seu primeiro acesso.");
        }
    }

    public void validaPermissaoVisualizacao(Usuario usuarioAutenticado, Usuario usuarioDestino) {
        boolean isAdmin = usuarioAutenticado.getPerfil().getNome().equalsIgnoreCase("ADMIN");
        boolean seuUsuario = usuarioAutenticado.getId().equals(usuarioDestino.getId());

        if (!isAdmin && !seuUsuario){
            throw new BusinessException("Não permitido");
        }
    }
}
