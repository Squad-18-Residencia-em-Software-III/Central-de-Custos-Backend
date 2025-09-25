package com.example.demo.domain.services;

import com.example.demo.domain.dto.solicitacoes.CadastroUsuarioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.mapper.SolicitacoesMapper;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.MunicipioRepository;
import com.example.demo.domain.repositorios.SolicitacaoCadastroUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class SolicitacoesCadastroService {

    private final EstruturaRepository estruturaRepository;
    private final MunicipioRepository municipioRepository;
    private final SolicitacaoCadastroUsuarioRepository solicitacaoRepository;
    private final SolicitacoesMapper solicitacoesMapper;

    public SolicitacoesCadastroService(EstruturaRepository estruturaRepository, MunicipioRepository municipioRepository, SolicitacaoCadastroUsuarioRepository solicitacaoRepository, SolicitacoesMapper solicitacoesMapper) {
        this.estruturaRepository = estruturaRepository;
        this.municipioRepository = municipioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.solicitacoesMapper = solicitacoesMapper;
    }

    @Transactional
    public void solicitarCadastro(CadastroUsuarioDto dto){
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

}
