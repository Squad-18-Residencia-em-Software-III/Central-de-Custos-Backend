package com.example.demo.domain.services.estrutura;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.mapper.EstruturaMapper;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.specs.ComboSpecs;
import com.example.demo.domain.repositorios.specs.EstruturaSpecs;
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
public class EstruturaService {

    private final EstruturaRepository estruturaRepository;
    private final EstruturaMapper estruturaMapper;
    private final EstruturaValidator estruturaValidator;

    public EstruturaService(EstruturaRepository estruturaRepository, EstruturaMapper estruturaMapper, EstruturaValidator estruturaValidator) {
        this.estruturaRepository = estruturaRepository;
        this.estruturaMapper = estruturaMapper;
        this.estruturaValidator = estruturaValidator;
    }

    public List<EstruturaDto> buscarEstruturas(String nome){
        Specification<Estrutura> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(EstruturaSpecs.comNomeContendo(nome));
        }

        List<Estrutura> estruturas = estruturaRepository.findAll(spec);
        return estruturas.stream().map(estruturaMapper::toDto).toList();
    }

    public Page<EstruturaDto> buscarSubSetores(int pageNumber, UUID estruturaId, String nome, ClassificacaoEstrutura classificacaoEstrutura){
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);

        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        estruturaValidator.validarAcessoBuscar(usuario, estrutura);

        Specification<Estrutura> spec = Specification.allOf(
                EstruturaSpecs.pertenceAEstruturaPai(estrutura)
        );

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(EstruturaSpecs.comNomeContendo(nome));
        }

        if (classificacaoEstrutura != null) {
            spec = spec.and(EstruturaSpecs.comClassificacao(classificacaoEstrutura));
        }
        Page<Estrutura> estruturas = estruturaRepository.findAll(spec, pageable);
        return estruturas.map(estruturaMapper::toDto);
    }

}
