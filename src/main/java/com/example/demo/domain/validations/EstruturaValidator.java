package com.example.demo.domain.validations;

import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.MunicipioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EstruturaValidator {

    private final EstruturaRepository estruturaRepository;
    private final MunicipioRepository municipioRepository;

    public EstruturaValidator(EstruturaRepository estruturaRepository, MunicipioRepository municipioRepository) {
        this.estruturaRepository = estruturaRepository;
        this.municipioRepository = municipioRepository;
    }

    public Estrutura validarEstruturaExiste(UUID estruturaId){
        return estruturaRepository.findById(estruturaId)
                .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));
    }

    public Municipio validarMunicipioExiste(Long municipioId){
        return municipioRepository.findById(municipioId)
                .orElseThrow(() -> new EntityNotFoundException("Municipio inválido ou inexistente"));
    }
}
