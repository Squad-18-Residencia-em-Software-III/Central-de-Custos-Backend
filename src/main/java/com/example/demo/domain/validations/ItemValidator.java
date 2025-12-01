package com.example.demo.domain.validations;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.ItemRepository;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemValidator {

    private final ItemRepository itemRepository;
    private final ComboRepository comboRepository;
    private final ValorItemComboRepository valorItemComboRepository;

    public ItemValidator(ItemRepository itemRepository, ComboRepository comboRepository, ValorItemComboRepository valorItemComboRepository) {
        this.itemRepository = itemRepository;
        this.comboRepository = comboRepository;
        this.valorItemComboRepository = valorItemComboRepository;
    }

    public void validaItemExisteNome(String nome){
        if (itemRepository.existsByNome(nome)){
            throw new AccessDeniedException("Já existe um item com este nome");
        }
    }

    public ItemCombo validaItemExiste(UUID itemId){
        return itemRepository.findByUuid(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item inválido ou inexistente"));
    }

    public void validaCombosPossuemItem(ItemCombo itemCombo){
        List<Combo> combos = comboRepository.findAllByItensContaining(itemCombo);

        if (!combos.isEmpty()){
            String nomesCombos = combos.stream()
                    .map(Combo::getNome)
                    .collect(Collectors.joining(", "));

            throw new BusinessException(
                    String.format("O item '%s' está sendo usado nos combos: %s",
                            itemCombo.getNome(), nomesCombos)
            );
        }
    }

    public void validaItemEstaNoCombo(Combo combo, ItemCombo itemCombo){
        List<ItemCombo> itens = combo.getItens();
        if (!itens.contains(itemCombo)){
            throw new BusinessException("O item não está presente no combo informado");
        }
    }

    public void validaExisteValorNoItem(ItemCombo itemCombo){
        if (valorItemComboRepository.existsByItemCombo(itemCombo)){
            throw new AccessDeniedException("Já existe um valor para este item, por tanto, não poderá ser deletado");
        }
    }

    public ValorItemCombo validaValorExiste(UUID valorItemComboId){
        return valorItemComboRepository.findByUuid(valorItemComboId)
                .orElseThrow(() -> new EntityNotFoundException("Valor inválido ou inexistente"));
    }
}
