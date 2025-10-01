package com.example.demo.domain.services;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.*;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.SolicitacaoValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SolicitacoesCadastroService {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;
    private final EstruturaValidator estruturaValidator;
    private final SolicitacaoValidator solicitacaoValidator;
    private final SolicitacoesMapper solicitacoesMapper;
    private final UsuarioService usuarioService;

    public SolicitacoesCadastroService(SolicitacaoCadastroUsuarioRepository solicitacaoRepository, EstruturaValidator estruturaValidator, SolicitacaoValidator solicitacaoValidator, SolicitacoesMapper solicitacoesMapper, UsuarioService usuarioService) {
        this.solicitacaoCadastroRepository = solicitacaoRepository;
        this.estruturaValidator = estruturaValidator;
        this.solicitacaoValidator = solicitacaoValidator;
        this.solicitacoesMapper = solicitacoesMapper;
        this.usuarioService = usuarioService;
    }

    // criar validações em classe separada
    @Transactional
    public void solicitarCadastro(SolicitaCadastroUsuarioDto dto){
        solicitacaoValidator.validaSolicitacaoCadastroExisteCpf(dto.cpf());
        Municipio municipio = estruturaValidator.validarMunicipioExiste(dto.municipioId());
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());

        SolicitacaoCadastroUsuario solicitacao = solicitacoesMapper.cadastroUsuarioToEntity(dto);
        solicitacao.setMunicipio(municipio);
        solicitacao.setEstrutura(estrutura);

        solicitacaoCadastroRepository.save(solicitacao);
    }

    // aprimorar busca
    public Page<CadastroUsuarioDto> listarSolicitacoesCadastro(int numeroPagina){
        Pageable pageable = PageRequest.of(numeroPagina - 1, 10);
        Page<SolicitacaoCadastroUsuario> solicitacoesCadastro = solicitacaoCadastroRepository.findAll(pageable);
        return solicitacoesCadastro.map(solicitacoesMapper::cadastroUsuarioToDto);
    }

    // colocar validação
    public CadastroUsuarioDto visualizarSolicitacaoCadastro(UUID id){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(id);
        return solicitacoesMapper.cadastroUsuarioToDto(solicitacao);
    }

    @Transactional
    public void aprovarOuReprovarSolicitacaoCadastro(UUID solicitacaoId, StatusSolicitacao statusSolicitacao){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(solicitacaoId);
        solicitacaoValidator.validaSolicitacaoCadastroPendente(solicitacao);

        switch (statusSolicitacao) {
            case APROVADA -> {
                usuarioService.criarUsuarioSolicitacaoCadastro(solicitacao);
                solicitacao.setStatus(StatusSolicitacao.APROVADA);
                solicitacaoCadastroRepository.save(solicitacao);
            }
            case RECUSADA -> {
                solicitacao.setStatus(StatusSolicitacao.RECUSADA);
                log.info("Solicitação com ID: {} recusada e removida.", solicitacao.getUuid());
                // Serviço de email
            }
        }
    }


}
