package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.InserirValorItemDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.ItemValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemValorService {

    private final EstruturaValidator estruturaValidator;
    private final ComboValidator comboValidator;
    private final ItemValidator itemValidator;
    private final ValorItemComboRepository valorItemComboRepository;

    public ItemValorService(EstruturaValidator estruturaValidator, ComboValidator comboValidator, ItemValidator itemValidator, ValorItemComboRepository valorItemComboRepository) {
        this.estruturaValidator = estruturaValidator;
        this.comboValidator = comboValidator;
        this.itemValidator = itemValidator;
        this.valorItemComboRepository = valorItemComboRepository;
    }


    @Transactional
    public void inserirValor(InserirValorItemDto dto){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());
        Combo combo = comboValidator.validarComboExiste(dto.comboId());
        Competencia competencia = comboValidator.validarCompetenciaExiste(dto.competenciaId());
        ItemCombo itemCombo = itemValidator.validaItemExiste(dto.itemId());

        comboValidator.validarCompetenciaAberta(competencia);

        Optional<ValorItemCombo> optionalValor = valorItemComboRepository
                .findByEstruturaAndComboAndItemComboAndCompetencia(estrutura, combo, itemCombo, competencia);

        ValorItemCombo valor;

        if (optionalValor.isPresent()) {
            valor = optionalValor.get();
            valor.setValor(dto.valor());
        } else {
            valor = new ValorItemCombo();
            valor.setEstrutura(estrutura);
            valor.setCombo(combo);
            valor.setItemCombo(itemCombo);
            valor.setCompetencia(competencia);
            valor.setValor(dto.valor());
        }

        valorItemComboRepository.save(valor);
    }
}
