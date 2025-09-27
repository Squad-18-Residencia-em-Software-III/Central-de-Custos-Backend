package com.example.demo.domain.services;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioValidator usuarioValidator;
    private final EstruturaValidator estruturaValidator;
    private final TokensService tokensService;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioValidator usuarioValidator, EstruturaValidator estruturaValidator, TokensService tokensService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioValidator = usuarioValidator;
        this.estruturaValidator = estruturaValidator;
        this.tokensService = tokensService;
    }

    @Transactional
    public void criarUsuarioSolicitacaoCadastro(SolicitacaoCadastroUsuario solicitacao){
        estruturaValidator.validarMunicipioExiste(solicitacao.getMunicipio().getId());
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(solicitacao.getEstrutura().getId());

        Usuario novoUsuario = usuarioMapper.toEntity(solicitacao);
        String rh = "RH";
        String resonsavelSetor = "RESPONSAVEL_SETOR";

        if (estrutura.getNome().equals(rh)){
            Perfil perfilRh = usuarioValidator.validaPerfilExisteNome(rh);
            novoUsuario.setPerfil(perfilRh);

        } else {
            Perfil perfilResponsavelSetor = usuarioValidator.validaPerfilExisteNome(resonsavelSetor);
            novoUsuario.setPerfil(perfilResponsavelSetor);
        }

        novoUsuario.setSenha(bCryptPasswordEncoder.encode(RandomStringUtils.secureStrong().nextAlphanumeric(5, 20)));

        usuarioRepository.save(novoUsuario);
        tokensService.criarTokenPrimeiroAcesso(novoUsuario);
    }
}
