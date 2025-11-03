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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<CompetenciaDto> buscarCompetencias(int pageNumber, StatusCompetencia statusCompetencia){
        Pageable pageable = PageRequest.of(pageNumber - 1, 12, Sort.by("competencia").descending());

        Specification<Competencia> spec = Specification.allOf();

        if (statusCompetencia != null) {
            spec = spec.and(CompetenciaSpecs.doStatus(statusCompetencia));
        }

        Page<Competencia> competencias = competenciaRepository.findAll(spec, pageable);

        return competencias.map(competenciaMapper::toDto);
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
