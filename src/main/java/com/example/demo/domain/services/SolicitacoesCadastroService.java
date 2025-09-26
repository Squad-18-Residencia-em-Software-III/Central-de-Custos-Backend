package com.example.demo.domain.services;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.RespostaSolicitacao;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.solicitacoes.TipoToken;
import com.example.demo.domain.entities.solicitacoes.Tokens;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.mapper.UsuarioMapper;
import com.example.demo.domain.repositorios.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class SolicitacoesCadastroService {

    private final EstruturaRepository estruturaRepository;
    private final MunicipioRepository municipioRepository;
    private final SolicitacaoCadastroUsuarioRepository solicitacaoRepository;
    private final TokensRepository tokensRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final SolicitacoesMapper solicitacoesMapper;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SolicitacoesCadastroService(EstruturaRepository estruturaRepository, MunicipioRepository municipioRepository, SolicitacaoCadastroUsuarioRepository solicitacaoRepository, TokensRepository tokensRepository, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, SolicitacoesMapper solicitacoesMapper, UsuarioMapper usuarioMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.estruturaRepository = estruturaRepository;
        this.municipioRepository = municipioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.tokensRepository = tokensRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.solicitacoesMapper = solicitacoesMapper;
        this.usuarioMapper = usuarioMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // criar validações em classe separada
    @Transactional
    public void solicitarCadastro(SolicitaCadastroUsuarioDto dto){
        if (solicitacaoRepository.existsByCpf(dto.cpf())){
            throw new AccessDeniedException("Já existe uma solicitação para este CPF");
        }

        Municipio municipio = municipioRepository.findById(dto.municipioId())
                .orElseThrow(() -> new EntityNotFoundException("Municipio inválido ou inexistente"));

        Estrutura estrutura = estruturaRepository.findById(dto.estruturaId())
                .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));

        SolicitacaoCadastroUsuario solicitacao = solicitacoesMapper.cadastroUsuarioToEntity(dto);
        solicitacao.setMunicipio(municipio);
        solicitacao.setEstrutura(estrutura);

        solicitacaoRepository.save(solicitacao);
    }

    // aprimorar busca
    public Page<CadastroUsuarioDto> listarSolicitacoesCadastro(int numeroPagina){
        Pageable pageable = PageRequest.of(numeroPagina - 1, 10);

        Page<SolicitacaoCadastroUsuario> solicitacoesCadastro = solicitacaoRepository.findAll(pageable);
        return solicitacoesCadastro.map(solicitacoesMapper::cadastroUsuarioToDto);
    }

    // colocar validação
    public CadastroUsuarioDto visualizarSolicitacaoCadastro(UUID id){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação inválida ou inexistente"));

        return solicitacoesMapper.cadastroUsuarioToDto(solicitacao);
    }

    @Transactional
    public void aprovarOuReprovarSolicitacaoCadastro(UUID solicitacaoId, RespostaSolicitacao respostaSolicitacao){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação inválida ou inexistente"));

        switch (respostaSolicitacao) {
            case APROVADA -> {
                Municipio municipio = municipioRepository.findById(solicitacao.getMunicipio().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Municipio inválido ou inexistente"));

                Estrutura estrutura = estruturaRepository.findById(solicitacao.getEstrutura().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));

                Usuario novoUsuario = usuarioMapper.toEntity(solicitacao);
                if (estrutura.getNome().equals("RH")){
                    String rh = "RH";
                    Perfil perfilRh = perfilRepository.findByNome(rh)
                                    .orElseThrow(() -> new EntityNotFoundException(String.format("Perfil %s não encontrado", rh)));
                    novoUsuario.setPerfil(perfilRh);
                    novoUsuario.setSenha(bCryptPasswordEncoder.encode(RandomStringUtils.secureStrong().nextAlphanumeric(5, 20)));

                    usuarioRepository.save(novoUsuario);

                    Tokens tokenPrimeiroAcesso = new Tokens();
                    tokenPrimeiroAcesso.setToken(RandomStringUtils.secureStrong().nextAlphanumeric(5, 10));
                    tokenPrimeiroAcesso.setTipo(TipoToken.PRIMEIRO_ACESSO);
                    tokenPrimeiroAcesso.setUsuario(novoUsuario);
                    tokenPrimeiroAcesso.setExpiraEm(LocalDateTime.now().plusDays(2));

                    tokensRepository.save(tokenPrimeiroAcesso);
                    // envia o email com o token

                } else {
                    String resonsavelSetor = "RESPONSAVEL_SETOR";
                    Perfil perfilResponsavelSetor = perfilRepository.findByNome(resonsavelSetor)
                            .orElseThrow(() -> new EntityNotFoundException(String.format("Perfil %s não encontrado", resonsavelSetor)));
                    novoUsuario.setPerfil(perfilResponsavelSetor);
                    novoUsuario.setSenha(bCryptPasswordEncoder.encode(RandomStringUtils.secureStrong().nextAlphanumeric(5, 10)));

                    usuarioRepository.save(novoUsuario);

                    Tokens tokenPrimeiroAcesso = new Tokens();
                    tokenPrimeiroAcesso.setToken(RandomStringUtils.secureStrong().nextAlphanumeric(5, 10));
                    tokenPrimeiroAcesso.setTipo(TipoToken.PRIMEIRO_ACESSO);
                    tokenPrimeiroAcesso.setUsuario(novoUsuario);
                    tokenPrimeiroAcesso.setExpiraEm(LocalDateTime.now().plusDays(2));

                    tokensRepository.save(tokenPrimeiroAcesso);
                    // envia o email com o token
                }
            }
            case RECUSADA -> {
                solicitacaoRepository.delete(solicitacao);
                log.info("Solicitação com ID: {} recusada e removida.", solicitacao.getId());
                // Serviço de email
            }
        }
    }


}
