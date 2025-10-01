package com.example.demo.domain.services;

import com.example.demo.domain.dto.security.AccessTokenDto;
import com.example.demo.domain.dto.security.LoginDto;
import com.example.demo.domain.dto.usuario.NovaSenhaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.enums.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.exceptions.SenhaInvalidaException;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.UsuarioRepository;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.SenhaValidator;
import com.example.demo.domain.validations.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioValidator usuarioValidator;
    private final EstruturaValidator estruturaValidator;
    private final TokensService tokensService;
    private final AuthService authService;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioValidator usuarioValidator, EstruturaValidator estruturaValidator, TokensService tokensService, AuthService authService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioValidator = usuarioValidator;
        this.estruturaValidator = estruturaValidator;
        this.tokensService = tokensService;
        this.authService = authService;
    }

    @Transactional
    public void criarUsuarioSolicitacaoCadastro(SolicitacaoCadastroUsuario solicitacao){
        estruturaValidator.validarMunicipioExiste(solicitacao.getMunicipio().getUuid());
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(solicitacao.getEstrutura().getUuid());

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
        tokensService.criarTokenPrimeiroAcesso(novoUsuario); // cria token
    }

    @Transactional
    public AccessTokenDto defineNovaSenhaUsuario(String codigoToken, String cpf, NovaSenhaDto senhaDto){ //param
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        Tokens token = tokensService.validarToken(codigoToken, cpf, TipoToken.PRIMEIRO_ACESSO);

        SenhaValidator.validarSenhaConfirma(senhaDto);
        List<String> errosSenha = SenhaValidator.verificarSenha(senhaDto.senha());
        if (!errosSenha.isEmpty()) {
            throw new SenhaInvalidaException(errosSenha);
        }

        usuario.setSenha(bCryptPasswordEncoder.encode(senhaDto.senha()));
        usuario.setPrimeiroAcesso(false);
        usuarioRepository.save(usuario);
        log.info("PRIMEIRO_ACESSO: Senha do usuario {} alterada com sucesso", usuario.getUuid());
        tokensService.deletarToken(token);

        return authService.login(new LoginDto(cpf, senhaDto.senha()));
    }

    public void solicitaRecuperarSenha(String cpf){
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        tokensService.criarTokenRecuperarSenha(usuario); // cria token
    }

    @Transactional
    public void defineSenhaRecuperarUsuario(String codigoToken, String cpf, NovaSenhaDto senhaDto){
        Usuario usuario = usuarioValidator.validaUsuarioCpf(cpf);
        Tokens token = tokensService.validarToken(codigoToken, cpf, TipoToken.RECUPERAR_SENHA);

        SenhaValidator.validarSenhaConfirma(senhaDto);
        List<String> errosSenha = SenhaValidator.verificarSenha(senhaDto.senha());
        if (!errosSenha.isEmpty()) {
            throw new SenhaInvalidaException(errosSenha);
        }

        usuario.setSenha(bCryptPasswordEncoder.encode(senhaDto.senha()));
        usuarioRepository.save(usuario);
        log.info("RECUPERAÇÃO: Senha do usuario {} alterada com sucesso", usuario.getUuid());
        tokensService.deletarToken(token);
    }
}
