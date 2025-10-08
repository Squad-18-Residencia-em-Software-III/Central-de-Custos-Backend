package com.example.demo.domain.validations;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ComboValidator {

    private final CompetenciaRepository competenciaRepository;
    private final ComboRepository comboRepository;

    public ComboValidator(CompetenciaRepository competenciaRepository, ComboRepository comboRepository) {
        this.competenciaRepository = competenciaRepository;
        this.comboRepository = comboRepository;
    }

    public Combo validarComboExiste(UUID comboId){
        return comboRepository.findByUuid(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo inválido ou inexistente"));
    }

    public void validarAcessoBuscarCombos(Usuario usuario, Estrutura estrutura) {
        boolean isAdmin = usuario.getPerfil().getNome().equals("ADMIN");
        boolean mesmaEstrutura = usuario.getEstrutura().equals(estrutura);
        boolean subSetor = usuario.getEstrutura().getSubSetores().contains(estrutura);

        if (!(isAdmin || mesmaEstrutura || subSetor)) {
            throw new AccessDeniedException("Busca não permitida");
        }
    }

    public Competencia validarCompetenciaExiste(UUID competenciaId){
        return competenciaRepository.findByUuid(competenciaId)
                .orElseThrow(() -> new EntityNotFoundException("Competencia inválida ou inexistente"));
    }

}
