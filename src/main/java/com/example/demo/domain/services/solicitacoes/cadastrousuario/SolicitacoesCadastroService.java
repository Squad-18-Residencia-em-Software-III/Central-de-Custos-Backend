package com.example.demo.domain.services.solicitacoes.cadastrousuario;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.dto.solicitacoes.SolicitaCadastroUsuarioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.*;
import com.example.demo.domain.services.solicitacoes.cadastrousuario.strategy.AceitarSolicitacaoCadastroStrategy;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.SolicitacaoValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SolicitacoesCadastroService {

    private final SolicitacaoCadastroUsuarioRepository solicitacaoCadastroRepository;
    private final EstruturaValidator estruturaValidator;
    private final SolicitacaoValidator solicitacaoValidator;
    private final SolicitacoesMapper solicitacoesMapper;
    private final List<AceitarSolicitacaoCadastroStrategy> aceitarSolicitacaoCadastroStrategies;

    public SolicitacoesCadastroService(SolicitacaoCadastroUsuarioRepository solicitacaoRepository, EstruturaValidator estruturaValidator, SolicitacaoValidator solicitacaoValidator, SolicitacoesMapper solicitacoesMapper, List<AceitarSolicitacaoCadastroStrategy> aceitarSolicitacaoCadastroStrategies) {
        this.solicitacaoCadastroRepository = solicitacaoRepository;
        this.estruturaValidator = estruturaValidator;
        this.solicitacaoValidator = solicitacaoValidator;
        this.solicitacoesMapper = solicitacoesMapper;
        this.aceitarSolicitacaoCadastroStrategies = aceitarSolicitacaoCadastroStrategies;
    }

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

    public Page<CadastroUsuarioDto> listarSolicitacoesCadastro(int numeroPagina){
        Pageable pageable = PageRequest.of(numeroPagina - 1, 10);
        Page<SolicitacaoCadastroUsuario> solicitacoesCadastro = solicitacaoCadastroRepository.findAll(pageable);
        return solicitacoesCadastro.map(solicitacoesMapper::cadastroUsuarioToDto);
    }

    public CadastroUsuarioDto visualizarSolicitacaoCadastro(UUID id){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(id);
        return solicitacoesMapper.cadastroUsuarioToDto(solicitacao);
    }

    @Transactional
    public void aprovarOuReprovarSolicitacaoCadastro(UUID solicitacaoId, StatusSolicitacao statusSolicitacao){
        SolicitacaoCadastroUsuario solicitacao = solicitacaoValidator.validaSolicitacaoCadastroExiste(solicitacaoId);
        solicitacaoValidator.validaSolicitacaoCadastroPendente(solicitacao);

        AceitarSolicitacaoCadastroStrategy strategy = aceitarSolicitacaoCadastroStrategies.stream()
                .filter(s -> s.statusSolicitacao(statusSolicitacao))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma implementação de aceite para a status utilizado"));

        strategy.realiza(solicitacao);
    }


}
