package com.example.demo.domain.services.combo.strategy;

import com.example.demo.domain.dto.combos.EditarComboDto;
import com.example.demo.domain.entities.combos.Combo;

public interface EditarCampoComboStrategy {

    void editar(Combo combo, EditarComboDto dto);
}
