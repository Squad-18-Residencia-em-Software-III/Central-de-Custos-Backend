package com.example.demo.domain.dto.combos;

import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.dto.estrutura.EstruturaDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ComboDetalhadoDto(
        UUID id,
        String nome,
        LocalDate competencia,
        List<EstruturaDto> estruturas,
        List<ItemDto> itens
) {
}
