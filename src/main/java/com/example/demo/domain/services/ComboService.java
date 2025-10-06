package com.example.demo.domain.services;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.ComboMapper;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.specs.ComboSpecs;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ComboService {

    private final ComboRepository comboRepository;
    private final ComboValidator comboValidator;
    private final ComboMapper comboMapper;

    public ComboService(ComboRepository comboRepository, ComboValidator comboValidator, ComboMapper comboMapper) {
        this.comboRepository = comboRepository;
        this.comboValidator = comboValidator;
        this.comboMapper = comboMapper;
    }

    public Page<ComboDto> buscarCombos(int pageNumber, Competencia competencia, Estrutura estrutura, String nome) {
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);

        comboValidator.validarAcessoBucarCombos(usuario, estrutura);

        Specification<Combo> spec = Specification.allOf(ComboSpecs.estruturaEqual(estrutura));

        if (competencia != null)
            spec = spec.and(ComboSpecs.competenciaEqual(competencia));

        if (nome != null && !nome.isBlank())
            spec = spec.and(ComboSpecs.comNomeContendo(nome));

        Page<Combo> combos = comboRepository.findAll(spec, pageable);

        return combos.map(comboMapper::toDto);
    }

}
