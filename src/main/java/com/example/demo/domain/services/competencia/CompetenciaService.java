package com.example.demo.domain.services.competencia;

import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.mapper.CompetenciaMapper;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import com.example.demo.domain.repositorios.specs.CompetenciaSpecs;
import com.example.demo.domain.repositorios.specs.ItemSpecs;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;
    private final CompetenciaMapper competenciaMapper;

    public CompetenciaService(CompetenciaRepository competenciaRepository, CompetenciaMapper competenciaMapper) {
        this.competenciaRepository = competenciaRepository;
        this.competenciaMapper = competenciaMapper;
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

}
