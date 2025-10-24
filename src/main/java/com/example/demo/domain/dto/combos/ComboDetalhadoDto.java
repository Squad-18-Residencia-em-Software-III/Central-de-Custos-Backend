package com.example.demo.domain.dto.combos;

import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.dto.estrutura.EstruturaDto;

import java.util.List;
import java.util.UUID;

public record ComboDetalhadoDto(
        UUID id,
        String nome,
        List<EstruturaDto> estruturas,
        List<ItemDto> itens
) {
}
