package com.example.demo.domain.services.estrutura;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.mapper.EstruturaMapper;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstruturaService {

    private final EstruturaRepository estruturaRepository;
    private final EstruturaMapper estruturaMapper;

    public EstruturaService(EstruturaRepository estruturaRepository, EstruturaMapper estruturaMapper) {
        this.estruturaRepository = estruturaRepository;
        this.estruturaMapper = estruturaMapper;
    }

    public List<EstruturaDto> buscarEstruturas(String nome){
        Specification<Estrutura> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(EstruturaSpecs.comNomeContendo(nome));
        }

        List<Estrutura> estruturas = estruturaRepository.findAll(spec);
        return estruturas.stream().map(estruturaMapper::toDto).toList();
    }

}
