package com.example.demo.domain.services.estrutura;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaInfoDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.EstruturaMapper;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EstruturaService {

    private final EstruturaRepository estruturaRepository;
    private final EstruturaMapper estruturaMapper;
    private final EstruturaValidator estruturaValidator;
    private final ObjectMapper objectMapper;

    public EstruturaService(EstruturaRepository estruturaRepository, EstruturaMapper estruturaMapper, EstruturaValidator estruturaValidator, ObjectMapper objectMapper) {
        this.estruturaRepository = estruturaRepository;
        this.estruturaMapper = estruturaMapper;
        this.estruturaValidator = estruturaValidator;
        this.objectMapper = objectMapper;
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


}
