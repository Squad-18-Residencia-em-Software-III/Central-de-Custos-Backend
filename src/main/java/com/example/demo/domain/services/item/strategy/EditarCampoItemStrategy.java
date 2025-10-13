package com.example.demo.domain.services.item.strategy;

import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.entities.combos.ItemCombo;

public interface EditarCampoItemStrategy {

    void editar(ItemCombo itemCombo, EditarItemDto dto);
}
