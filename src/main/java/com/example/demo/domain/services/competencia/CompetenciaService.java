package com.example.demo.domain.services.competencia;

import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.mapper.CompetenciaMapper;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import com.example.demo.domain.repositorios.specs.CompetenciaSpecs;
import com.example.demo.domain.validations.ComboValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;
    private final CompetenciaMapper competenciaMapper;
    private final ComboValidator comboValidator;

    public CompetenciaService(CompetenciaRepository competenciaRepository, CompetenciaMapper competenciaMapper, ComboValidator comboValidator) {
        this.competenciaRepository = competenciaRepository;
        this.competenciaMapper = competenciaMapper;
        this.comboValidator = comboValidator;
    }

    public Competencia getCompetenciaAtual() {
        LocalDate hoje = LocalDate.now();
        return competenciaRepository.findByCompetencia(LocalDate.of(hoje.getYear(), hoje.getMonth(), 1))
                .orElseThrow(() -> new EntityNotFoundException("Competência atual não encontrada"));
    }

    public List<CompetenciaDto> buscarCompetencias(StatusCompetencia statusCompetencia){
        Specification<Competencia> spec = Specification.allOf();

        if (statusCompetencia != null) {
            spec = spec.and(CompetenciaSpecs.doStatus(statusCompetencia));
        }

        List<Competencia> competencias = competenciaRepository.findAll(spec);

        return competencias.stream().map(competenciaMapper::toDto).toList();
    }

    public void criarCompetencia(LocalDate dataCompetencia){
        comboValidator.validarCompetenciaExiste(dataCompetencia);
        Competencia competencia = new Competencia();
        competencia.setCompetencia(dataCompetencia);
        competenciaRepository.save(competencia);
    }

    public void definirStatusCompetencia(UUID competenciaId, StatusCompetencia statusCompetencia){
        Competencia competencia = comboValidator.validarCompetenciaExiste(competenciaId);

        switch (statusCompetencia){
            case ABERTA -> competencia.setStatusCompetencia(StatusCompetencia.ABERTA);

            case FECHADA -> competencia.setStatusCompetencia(StatusCompetencia.FECHADA);

            default -> throw new BusinessException("Status da competencia não reconhecido: " + statusCompetencia);
        }

        competenciaRepository.save(competencia);
        log.info("Competencia {} teve seu status definido para {}", competenciaId, statusCompetencia);
    }

}
