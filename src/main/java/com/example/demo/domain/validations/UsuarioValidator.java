package com.example.demo.domain.validations;

import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
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

    public void validaUsuarioAutenticadoIdentificavel(Usuario usuarioAutenticado) {
        UUID uuid = usuarioAutenticado.getUuid();
        String cpf = usuarioAutenticado.getCpf();

        if (uuid == null && (cpf == null || cpf.isBlank())) {
            throw new AccessDeniedException("Não foi possível identificar o usuário autenticado. Informe uuid ou cpf.");
        }
    }

    public void validaPermissaoVisualizacao(Usuario usuarioAutenticado, UUID uuidAlvo, String cpfAlvo) {
        boolean isAdmin = usuarioAutenticado.getAuthorities()
                .stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        if (isAdmin) {
            return;
        }

        UUID uuidAutenticado = usuarioAutenticado.getUuid();
        String cpfAutenticado = usuarioAutenticado.getCpf();

        boolean temPermissao = false;
        if (uuidAlvo != null) {
            temPermissao = uuidAlvo.equals(uuidAutenticado);
        } else if (cpfAlvo != null && !cpfAlvo.isBlank()) {
            temPermissao = cpfAlvo.equals(cpfAutenticado);
        }

        if (!temPermissao) {
            throw new AccessDeniedException("Acesso negado: só é possível visualizar o próprio perfil.");
        }
    }
}
