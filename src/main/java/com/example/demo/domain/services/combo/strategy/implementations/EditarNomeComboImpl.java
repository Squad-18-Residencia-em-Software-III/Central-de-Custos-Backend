package com.example.demo.domain.services.combo.strategy.implementations;

import com.example.demo.domain.dto.combos.EditarComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.services.combo.strategy.EditarCampoComboStrategy;
import org.springframework.stereotype.Component;

@Component
public class EditarNomeComboImpl implements EditarCampoComboStrategy {

    @Override
    public void editar(Combo combo, EditarComboDto dto) {
        if (dto.nome() != null && !dto.nome().isBlank()) {
            combo.setNome(dto.nome());
        }
    }
}
