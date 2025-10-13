package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.entities.combos.Combo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComboMapper {

    @Mapping(target = "id", source = "uuid")
    ComboDto toDto(Combo combo);

    @Mapping(target = "estruturas", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Combo toEntity(CriarComboDto dto);

}
