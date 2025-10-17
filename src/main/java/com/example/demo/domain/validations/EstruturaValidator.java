package com.example.demo.domain.validations;

import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.exceptions.BusinessException;
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
        return estruturaRepository.findByUuid(estruturaId)
                .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));
    }

    public Municipio validarMunicipioExiste(UUID municipioId){
        return municipioRepository.findByUuid(municipioId)
                .orElseThrow(() -> new EntityNotFoundException("Municipio inválido ou inexistente"));
    }

    public void validaUsuarioPertenceEstrutura(Usuario usuario, Estrutura estrutura){
        Estrutura estruturaUsuario = usuario.getEstrutura();
        if (!estruturaUsuario.equals(estrutura)){
            throw new BusinessException("O setor informado não pertence ao seu usuário");
        }
    }

    public void validaUsuarioPertenceEstrutura(Usuario usuario, String nome){
        Estrutura estruturaUsuario = usuario.getEstrutura();
        Estrutura estrutura = estruturaRepository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));

        if (!estruturaUsuario.equals(estrutura)){
            throw new BusinessException("O setor informado não pertence ao seu usuário");
        }
    }
}
