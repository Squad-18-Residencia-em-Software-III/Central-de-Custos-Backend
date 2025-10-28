package com.example.demo.domain.validations;

import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.MunicipioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
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

        boolean isAdmin = usuario.getPerfil().getNome().equalsIgnoreCase("ADMIN");

        if (!isAdmin && !(estruturaUsuario.getId()).equals(estrutura.getId())){
            throw new BusinessException("O setor informado não pertence ao seu usuário");
        }
    }

    public void validaUsuarioPertenceEstrutura(Usuario usuario, ClassificacaoEstrutura classificacaoEstrutura){
        Estrutura estruturaUsuario = usuario.getEstrutura();
        Estrutura estrutura = estruturaRepository.findByClassificacaoEstrutura(classificacaoEstrutura)
                .orElseThrow(() -> new EntityNotFoundException("Setor inválido ou inexistente"));

        boolean isAdmin = usuario.getPerfil().getNome().equalsIgnoreCase("ADMIN");

        if (!isAdmin && !(estruturaUsuario.getId()).equals(estrutura.getId())){
            throw new BusinessException("O setor informado não pertence ao seu usuário");
        }
    }

    public void validaClassificacaoEstrutura(Estrutura estrutura, ClassificacaoEstrutura classificacaoEstrutura){
        if (!estrutura.getClassificacaoEstrutura().equals(classificacaoEstrutura)){
            throw new BusinessException("O setor informado não pertence a classificação necessária");
        }
    }

    public void validarAcessoBuscar(Usuario usuario, UUID estrutura) {
        Estrutura estruturaUsuario = usuario.getEstrutura();

        Estrutura estruturaSelecionada = validarEstruturaExiste(estrutura);

        boolean isAdmin = usuario.getPerfil().getNome().equalsIgnoreCase("ADMIN");
        boolean mesmaEstrutura = (estruturaUsuario.getId()).equals(estruturaSelecionada.getId());
        boolean contemNosSubSetores = estruturaRepository.pertenceAHierarquia(estruturaUsuario.getId(), estruturaSelecionada.getId());

        if (!isAdmin && !mesmaEstrutura && !contemNosSubSetores) {
            throw new AccessDeniedException("Não permitido");
        }
    }
}