package com.example.demo.domain.dto.combos;

import com.example.demo.domain.dto.estrutura.EstruturaComboDto;

import java.util.List;
import java.util.UUID;

public record ComboDetalhadoDto(
        UUID id,
        String nome,
        String competencia,
        List<EstruturaComboDto> estruturas
) {
}
