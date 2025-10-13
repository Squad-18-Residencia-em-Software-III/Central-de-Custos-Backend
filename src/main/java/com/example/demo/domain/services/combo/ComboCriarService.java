package com.example.demo.domain.services.combo;

import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.dto.combos.EditarComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.mapper.ComboMapper;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.services.combo.strategy.EditarCampoComboStrategy;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.ItemValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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
        List<Estrutura> estruturas = dto.estruturas().stream()
                .map(estruturaValidator::validarEstruturaExiste)
                .toList();

        List<ItemCombo> itens = dto.itens().stream()
                .map(itemValidator::validaItemExiste)
                .toList();

        Combo combo = comboMapper.toEntity(dto);
        combo.setEstruturas(estruturas);
        combo.setItens(itens);

        comboRepository.save(combo);
    }

    @Transactional
    public void adicionarEstruturaAoCombo(UUID comboId, UUID estruturaId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);

        combo.getEstruturas().add(estrutura);
        estrutura.getCombos().add(combo); // atualiza o outro lado

        comboRepository.save(combo);
    }

    @Transactional
    public void adicionarItemAoCombo(UUID comboId, UUID itemId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        ItemCombo itemCombo = itemValidator.validaItemExiste(itemId);

        combo.getItens().add(itemCombo);
        comboRepository.save(combo);
    }

    @Transactional
    public void removerItemDoCombo(UUID comboId, UUID itemId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        ItemCombo itemCombo = itemValidator.validaItemExiste(itemId);
        itemValidator.validaExisteValorNoItem(itemCombo);

        combo.getItens().remove(itemCombo);
        comboRepository.save(combo);
    }

    @Transactional
    public void removerEstruturaDoCombo(UUID comboId, UUID estruturaId) {
        Combo combo = comboValidator.validarComboExiste(comboId);
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);

        combo.getEstruturas().remove(estrutura);
        estrutura.getCombos().remove(combo);
        comboRepository.save(combo);
    }

    @Transactional
    public void editarCombo(UUID comboId, EditarComboDto dto){
        Combo combo = comboValidator.validarComboExiste(comboId);
        editarCampoComboStrategies.forEach(s -> s.editar(combo, dto));
        comboRepository.save(combo);
    }
}
