package com.example.demo.domain.services.estrutura;

import com.example.demo.domain.dto.estrutura.CompetenciaAlunoEstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaInfoDto;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.CompetenciaAlunoEstrutura;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.mapper.EstruturaMapper;
import com.example.demo.domain.repositorios.CompetenciaAlunoEstruturaRepository;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class EstruturaService {

    private final EstruturaRepository estruturaRepository;
    private final CompetenciaAlunoEstruturaRepository competenciaAlunoEstruturaRepository;
    private final EstruturaMapper estruturaMapper;
    private final EstruturaValidator estruturaValidator;
    private final ObjectMapper objectMapper;
    private final ComboValidator comboValidator;

    public EstruturaService(EstruturaRepository estruturaRepository, CompetenciaAlunoEstruturaRepository competenciaAlunoEstruturaRepository, EstruturaMapper estruturaMapper, EstruturaValidator estruturaValidator, ObjectMapper objectMapper, ComboValidator comboValidator) {
        this.estruturaRepository = estruturaRepository;
        this.competenciaAlunoEstruturaRepository = competenciaAlunoEstruturaRepository;
        this.estruturaMapper = estruturaMapper;
        this.estruturaValidator = estruturaValidator;
        this.objectMapper = objectMapper;
        this.comboValidator = comboValidator;
    }

    public Page<EstruturaDto> buscarEstruturas(int pageNumber, String nome){
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);

        Specification<Estrutura> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(EstruturaSpecs.comNomeContendo(nome));
        }

        Page<Estrutura> estruturas = estruturaRepository.findAll(spec, pageable);
        return estruturas.map(estruturaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EstruturaInfoDto buscarInfoEstrutura(UUID estruturaId) {
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();

        UUID estrutura;

        if (estruturaId != null){
            estrutura = estruturaId;
        } else {
            estrutura = usuario.getEstrutura().getUuid();
        }

        estruturaValidator.validarAcessoBuscar(usuario, estrutura);
        String json = estruturaRepository.buscarInfoEstruturaJson(estrutura);
        if (json == null) {
            throw new IllegalArgumentException("Estrutura não encontrada para o ID informado: " + estrutura);
        }

        try {
            return objectMapper.readValue(json, EstruturaInfoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para EstruturaInfoDto", e);
        }
    }

    @Transactional
    public void inserirNumeroAlunosCompetencia(CompetenciaAlunoEstruturaDto dto){
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();

        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());
        estruturaValidator.validaUsuarioPertenceEstrutura(usuario, estrutura);
        estruturaValidator.validaClassificacaoEstrutura(estrutura, ClassificacaoEstrutura.ESCOLA);
        Competencia competencia = comboValidator.validarCompetenciaExiste(dto.competenciaId());
        comboValidator.validarCompetenciaAberta(competencia);

        Optional<CompetenciaAlunoEstrutura> optionalCompetenciaAlunoEstrutura = competenciaAlunoEstruturaRepository.findByEstruturaAndCompetencia(estrutura, competencia);
        CompetenciaAlunoEstrutura competenciaAlunoEstrutura;

        if (optionalCompetenciaAlunoEstrutura.isEmpty()){
            competenciaAlunoEstrutura = new CompetenciaAlunoEstrutura();
            competenciaAlunoEstrutura.setEstrutura(estrutura);
            competenciaAlunoEstrutura.setCompetencia(competencia);
            competenciaAlunoEstrutura.setNumeroAlunos(dto.numeroAlunos());
        } else {
            competenciaAlunoEstrutura = optionalCompetenciaAlunoEstrutura.get();
            competenciaAlunoEstrutura.setNumeroAlunos(dto.numeroAlunos());
        }

        competenciaAlunoEstruturaRepository.save(competenciaAlunoEstrutura);
    }

    public Optional<CompetenciaAlunoEstruturaDto> buscarNumeroAlunosCompetencia(UUID estruturaUuid, UUID competenciaUuid) {
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaUuid);
        Competencia competencia = comboValidator.validarCompetenciaExiste(competenciaUuid);

        return competenciaAlunoEstruturaRepository.findByEstruturaAndCompetencia(estrutura, competencia)
                .map(estruturaMapper::competenciaAlunoToDto);
    }
// teste

}
