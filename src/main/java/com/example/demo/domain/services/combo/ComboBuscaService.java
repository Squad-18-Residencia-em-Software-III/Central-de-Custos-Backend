package com.example.demo.domain.services.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.ComboMapper;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.specs.ComboSpecs;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComboBuscaService {

    private final ComboRepository comboRepository;
    private final ComboValidator comboValidator;
    private final ComboMapper comboMapper;
    private final EstruturaValidator estruturaValidator;

    public ComboBuscaService(ComboRepository comboRepository, ComboValidator comboValidator, ComboMapper comboMapper, EstruturaValidator estruturaValidator) {
        this.comboRepository = comboRepository;
        this.comboValidator = comboValidator;
        this.comboMapper = comboMapper;
        this.estruturaValidator = estruturaValidator;
    }

    public List<ComboDto> buscarCombosEstrutura(UUID estruturaId, String nome) {
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();

        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        comboValidator.validarAcessoBuscarCombos(usuario, estrutura);

        Specification<Combo> spec = Specification.allOf(
                ComboSpecs.estruturaEqual(estrutura)
        );

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(ComboSpecs.comNomeContendo(nome));
        }

        List<Combo> combos = comboRepository.findAll(spec);

        return combos.stream().map(comboMapper::toDto).toList();
    }

    public Page<ComboDto> buscarCombos(int pageNumber, String nome) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);

        Specification<Combo> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(ComboSpecs.comNomeContendo(nome));
        }

        Page<Combo> combos = comboRepository.findAll(spec, pageable);

        return combos.map(comboMapper::toDto);
    }

}
