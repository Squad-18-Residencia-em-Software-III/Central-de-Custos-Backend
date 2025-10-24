package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.combos.ComboDetalhadoDto;
import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.entities.combos.Combo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { EstruturaMapper.class, ItemMapper.class } // mappers auxiliares
)
public interface ComboMapper {

    @Mapping(target = "id", source = "uuid")
    ComboDto toDto(Combo combo);

    @Mapping(target = "estruturas", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Combo toEntity(CriarComboDto dto);

    @Mapping(source = "uuid", target = "id")
    ComboDetalhadoDto toDtoInfo(Combo combo);


}
