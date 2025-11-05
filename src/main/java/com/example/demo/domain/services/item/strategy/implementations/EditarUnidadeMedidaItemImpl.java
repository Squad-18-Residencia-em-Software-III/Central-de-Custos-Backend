package com.example.demo.domain.services.item.strategy.implementations;

import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.services.item.strategy.EditarCampoItemStrategy;

public class EditarUnidadeMedidaItemImpl implements EditarCampoItemStrategy {

    @Override
    public void editar(ItemCombo item, EditarItemDto dto) {
        if (dto.unidadeMedida() != null) {
            item.setUnidadeMedida(dto.unidadeMedida());
        }
    }
}
