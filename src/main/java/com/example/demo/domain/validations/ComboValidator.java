package com.example.demo.domain.validations;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.StatusCompetencia;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.CompetenciaRepository;
import com.example.demo.domain.repositorios.EstruturaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void validarAcessoBuscarCombos(Usuario usuario, Estrutura estruturaCombo) {
        Estrutura estruturaUsuario = usuario.getEstrutura();

        boolean isAdmin = usuario.getPerfil().getNome().equalsIgnoreCase("ADMIN");
        boolean mesmaEstrutura = estruturaUsuario.equals(estruturaCombo);
        boolean contemNosSubSetores = contemSubSetorRecursivo(estruturaUsuario, estruturaCombo);

        if (!isAdmin && !mesmaEstrutura && !contemNosSubSetores) {
            throw new AccessDeniedException("Não permitido");
        }
    }

    private boolean contemSubSetorRecursivo(Estrutura estruturaPai, Estrutura estruturaProcurada) {
        if (estruturaPai.getSubSetores().isEmpty()) {
            return false;
        }

        for (Estrutura sub : estruturaPai.getSubSetores()) {
            if (sub.equals(estruturaProcurada) || contemSubSetorRecursivo(sub, estruturaProcurada)) {
                return true;
            }
        }
        return false;
    }

    public Competencia validarCompetenciaExiste(UUID competenciaId){
        return competenciaRepository.findByUuid(competenciaId)
                .orElseThrow(() -> new EntityNotFoundException("Competencia inválida ou inexistente"));
    }

    public void validarCompetenciaAberta(Competencia competencia){
        if (!competencia.getStatusCompetencia().equals(StatusCompetencia.ABERTA)){
            throw new BusinessException("A competencia está fechada, por tanto, não será possível inserir um valor");
        }
    }

}
