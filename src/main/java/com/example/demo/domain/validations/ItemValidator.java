package com.example.demo.domain.validations;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.repositorios.ComboRepository;
import com.example.demo.domain.repositorios.ItemRepository;
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

    public ItemValidator(ItemRepository itemRepository, ComboRepository comboRepository) {
        this.itemRepository = itemRepository;
        this.comboRepository = comboRepository;
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
}
