package com.example.demo.domain.services.solicitacoes.cadastrousuario;

import com.example.demo.domain.dto.solicitacoes.cadastrousuario.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.cadastrousuario.CadastroUsuarioInfoDto;
import com.example.demo.domain.dto.solicitacoes.cadastrousuario.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.*;
import com.example.demo.domain.repositorios.specs.SolicitacoesSpecs;
import com.example.demo.domain.services.solicitacoes.factory.SolicitacaoFactory;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.SolicitacaoValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SolicitacoesCadastroService {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;
    private final EstruturaValidator estruturaValidator;
    private final SolicitacaoValidator solicitacaoValidator;
    private final SolicitacoesMapper solicitacoesMapper;
    private final SolicitacaoFactory solicitacaoFactory;

    public SolicitacoesCadastroService(SolicitacaoCadastroUsuarioRepository solicitacaoRepository, EstruturaValidator estruturaValidator, SolicitacaoValidator solicitacaoValidator, SolicitacoesMapper solicitacoesMapper, SolicitacaoFactory solicitacaoFactory) {
        this.solicitacaoCadastroRepository = solicitacaoRepository;
        this.estruturaValidator = estruturaValidator;
        this.solicitacaoValidator = solicitacaoValidator;
        this.solicitacoesMapper = solicitacoesMapper;
        this.solicitacaoFactory = solicitacaoFactory;
    }

    @Transactional
    public void solicitarCadastro(SolicitaCadastroUsuarioDto dto){
        solicitacaoValidator.validaSolicitacaoCadastroExisteCpf(dto.cpf());
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());

        SolicitacaoCadastroUsuario solicitacao = solicitacoesMapper.cadastroUsuarioToEntity(dto);
        solicitacao.setEstrutura(estrutura);

        solicitacaoCadastroRepository.save(solicitacao);
    }

    public Page<CadastroUsuarioDto> listarSolicitacoesCadastro(int pageNumber, String nome, StatusSolicitacao statusSolicitacao){
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);

        Specification<SolicitacaoCadastroUsuario> spec = Specification.allOf();

        if (nome != null){
            spec = spec.and(SolicitacoesSpecs.comNomeContendo(nome));
        }

        if (statusSolicitacao != null){
            spec = spec.and(SolicitacoesSpecs.doStatusCadastro(statusSolicitacao));
        }

        Page<SolicitacaoCadastroUsuario> solicitacoesCadastro = solicitacaoCadastroRepository.findAll(spec, pageable);

        return solicitacoesCadastro.map(solicitacoesMapper::cadastroUsuarioToDto);
    }

    public CadastroUsuarioInfoDto visualizarSolicitacaoCadastro(UUID id){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(id);
        return solicitacoesMapper.cadastroUsuarioToInfoDto(solicitacao);
    }

    @Transactional
    public void aprovarOuReprovarSolicitacaoCadastro(UUID solicitacaoId, StatusSolicitacao statusSolicitacao){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(solicitacaoId);
        solicitacaoValidator.validaSolicitacaoCadastroPendente(solicitacao);
        solicitacaoFactory.solicitacaoCadastroStatus(statusSolicitacao).realiza(solicitacao);
    }


}
