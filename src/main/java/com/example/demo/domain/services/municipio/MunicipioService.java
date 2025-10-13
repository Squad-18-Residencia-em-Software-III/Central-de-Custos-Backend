package com.example.demo.domain.services.municipio;

import com.example.demo.domain.dto.municipio.MunicipioDto;
import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.mapper.MunicipioMapper;
import com.example.demo.domain.repositorios.MunicipioRepository;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
import com.example.demo.domain.repositorios.specs.MunicipioSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final MunicipioMapper municipioMapper;

    public MunicipioService(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }

    public List<MunicipioDto> buscarMunicipios(String nome){
        Specification<Municipio> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(MunicipioSpecs.comNomeContendo(nome));
        }

        List<Municipio> municipios = municipioRepository.findAll(spec);
        return municipios.stream().map(municipioMapper::toDto).toList();
    }
}
