package com.example.demo.domain.services.combo;

import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.dto.combos.EditarComboDto;
import com.example.demo.domain.dto.combos.InclusaoDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.mapper.ComboMapper;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.services.combo.strategy.EditarCampoComboStrategy;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.ItemValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComboCriarService {

    private final EstruturaValidator estruturaValidator;
    private final ItemValidator itemValidator;
    private final ComboRepository comboRepository;
    private final ComboValidator comboValidator;
    private final ComboMapper comboMapper;
    private final List<EditarCampoComboStrategy> editarCampoComboStrategies;

    public ComboCriarService(EstruturaValidator estruturaValidator, ItemValidator itemValidator, ComboRepository comboRepository, ComboValidator comboValidator, ComboMapper comboMapper, List<EditarCampoComboStrategy> editarCampoComboStrategies) {
        this.estruturaValidator = estruturaValidator;
        this.itemValidator = itemValidator;
        this.comboRepository = comboRepository;
        this.comboValidator = comboValidator;
        this.comboMapper = comboMapper;
        this.editarCampoComboStrategies = editarCampoComboStrategies;
    }

    @Transactional
    public void criarCombo(CriarComboDto dto){
        Competencia competencia = comboValidator.validarCompetenciaExiste(dto.competenciaId());
        comboValidator.validarCompetenciaAberta(competencia);
        List<Estrutura> estruturas = dto.estruturas().stream()
                .map(estruturaValidator::validarEstruturaExiste)
                .toList();

        List<ItemCombo> itens = dto.itens().stream()
                .map(itemValidator::validaItemExiste)
                .toList();

        Combo combo = comboMapper.toEntity(dto);
        combo.setCompetencia(competencia);
        combo.setEstruturas(estruturas);
        combo.setItens(itens);

        comboRepository.save(combo);
    }

    @Transactional
    public void adicionarEstruturasAoCombo(UUID comboId, InclusaoDto dto) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarCompetenciaAberta(combo.getCompetencia());
        Set<Estrutura> estruturasExistentes = new HashSet<>(combo.getEstruturas());

        List<Estrutura> estruturasParaAdicionar = dto.ids().stream()
                .map(estruturaValidator::validarEstruturaExiste)
                .filter(estrutura -> {
                    if (estruturasExistentes.contains(estrutura)){
                        throw new BusinessException(
                                String.format("A estrutura %s já faz parte desse combo", estrutura.getNome())
                        );
                    }
                    return true;
                })
                .toList();

        combo.getEstruturas().addAll(estruturasParaAdicionar);

        comboRepository.save(combo);
    }

    @Transactional
    public void adicionarItensAoCombo(UUID comboId, InclusaoDto dto) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarCompetenciaAberta(combo.getCompetencia());
        Set<ItemCombo> itensExistentes = new HashSet<>(combo.getItens());

        List<ItemCombo> itensParaAdicionar = dto.ids().stream()
                .map(itemValidator::validaItemExiste)
                .filter(item -> {
                    if (itensExistentes.contains(item)) {
                        throw new BusinessException(
                                String.format("O item %s já faz parte desse combo", item.getNome())
                        );
                    }
                    return true;
                })
                .toList();

        combo.getItens().addAll(itensParaAdicionar);

        comboRepository.save(combo);
    }

    @Transactional
    public void removerItemDoCombo(UUID comboId, UUID itemId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarCompetenciaAberta(combo.getCompetencia());
        ItemCombo itemCombo = itemValidator.validaItemExiste(itemId);
        itemValidator.validaExisteValorNoItem(itemCombo);

        combo.getItens().remove(itemCombo);
        comboRepository.save(combo);
    }

    @Transactional
    public void removerEstruturaDoCombo(UUID comboId, UUID estruturaId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarCompetenciaAberta(combo.getCompetencia());
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);

        combo.getEstruturas().remove(estrutura);
        estrutura.getCombos().remove(combo);
        comboRepository.save(combo);
    }

    @Transactional
    public void editarCombo(UUID comboId, EditarComboDto dto){
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarCompetenciaAberta(combo.getCompetencia());
        editarCampoComboStrategies.forEach(s -> s.editar(combo, dto));
        comboRepository.save(combo);
    }

    @Transactional
    public void clonarCombo(UUID comboId, UUID competenciaId, boolean clonarEstruturas){
        Combo combo = comboValidator.validarComboExiste(comboId);
        Competencia competencia = comboValidator.validarCompetenciaExiste(competenciaId);
        comboValidator.validarCompetenciaAberta(competencia);
        comboValidator.validarComboJaExiste(combo.getNome(), competencia);

        Combo novoCombo = comboMapper.clonarCombo(combo);
        if (clonarEstruturas){
            novoCombo.setEstruturas(new ArrayList<>(combo.getEstruturas()));
        }
        novoCombo.setItens(new ArrayList<>(combo.getItens()));
        novoCombo.setCompetencia(competencia);

        comboRepository.save(novoCombo);
    }
}
