package com.example.demo.domain.services.usuario;

import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.dto.usuario.UsuarioDto;
import com.example.demo.domain.dto.usuario.UsuarioInfoDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
import com.example.demo.domain.repositorios.specs.UsuarioSpecs;
import com.example.demo.domain.services.token.TokensService;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.demo.infra.security.authentication.AuthenticatedUserProvider.getAuthenticatedUser;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioValidator usuarioValidator;
    private final TokensService tokensService;
    private final UsuarioSenhaService usuarioSenhaService;
    private final UsuarioCriarService usuarioCriarService;
    private final UsuarioMapper usuarioMapper;
    private final EstruturaValidator estruturaValidator;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioValidator usuarioValidator, TokensService tokensService, UsuarioSenhaService usuarioSenhaService, UsuarioCriarService usuarioCriarService, UsuarioMapper usuarioMapper, EstruturaValidator estruturaValidator, UsuarioRepository usuarioRepository) {
        this.usuarioValidator = usuarioValidator;
        this.tokensService = tokensService;
        this.usuarioSenhaService = usuarioSenhaService;
        this.usuarioCriarService = usuarioCriarService;
        this.usuarioMapper = usuarioMapper;
        this.estruturaValidator = estruturaValidator;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void criarUsuarioSolicitacaoCadastro(SolicitacaoCadastroUsuario solicitacao){
        usuarioCriarService.criarUsuarioSolicitacaoCadastro(solicitacao);
    }

    @Transactional
    public AccessTokenDto defineNovaSenhaUsuario(String codigoToken, String cpf, NovaSenhaDto senhaDto){
        return usuarioSenhaService.definirSenha(codigoToken, cpf, senhaDto);
    }

    public void solicitaRecuperarSenha(String cpf){
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        tokensService.criarToken(usuario, TipoToken.RECUPERAR_SENHA); // cria token
    }

    public UsuarioInfoDto visualizarInfoUsuario(UUID uuid) {
        Usuario usuarioAutenticado = getAuthenticatedUser();
        Usuario usuario;
        if (uuid == null){
            usuario =  usuarioAutenticado;
        } else {
            usuario = usuarioValidator.validaUsuarioExisteUuid(uuid);
            usuarioValidator.validaPermissaoVisualizacao(usuarioAutenticado, usuario);
        }

        return usuarioMapper.usuarioToInfoDto(usuario);
    }

    public Page<UsuarioDto> buscarUsuarios(int pageNumber, String nome, UUID estruturaId, Boolean primeiroAcesso){
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);

        Specification<Usuario> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(UsuarioSpecs.comNomeContendo(nome));
        }

        if (estruturaId != null) {
            Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
            spec = spec.and(UsuarioSpecs.daEstrutura(estrutura));
        }

        if (primeiroAcesso != null) {
            spec = spec.and(UsuarioSpecs.doPrimeiroAcesso(primeiroAcesso));
        }

        Page<Usuario> usuarios = usuarioRepository.findAll(spec ,pageable);

        return usuarios.map(usuarioMapper::usuarioToDto);
    }
}
