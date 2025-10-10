package com.example.demo.domain.services.competencia;

import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaService(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    public Competencia getCompetenciaAtual() {
        LocalDate hoje = LocalDate.now();
        return competenciaRepository.findByCompetencia(LocalDate.of(hoje.getYear(), hoje.getMonth(), 1))
                .orElseThrow(() -> new EntityNotFoundException("Competência atual não encontrada"));
    }

}
