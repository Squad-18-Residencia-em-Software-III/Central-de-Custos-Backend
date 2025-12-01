package com.example.demo.domain.services.item.strategy.implementations;

import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.services.item.strategy.EditarCampoItemStrategy;
import org.springframework.stereotype.Component;

@Component
public class EditarNomeItemImpl implements EditarCampoItemStrategy {

    @Override
    public void editar(ItemCombo item, EditarItemDto dto) {
        if (dto.nome() != null && !dto.nome().isBlank()) {
            item.setNome(dto.nome());
        }
    }
}
