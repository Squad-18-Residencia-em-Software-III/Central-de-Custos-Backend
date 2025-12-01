package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.combos.item.CriarItemDto;
import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.entities.combos.ItemCombo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", source = "uuid")
    ItemDto toDto(ItemCombo itemCombo);

    ItemCombo toEntity(CriarItemDto dto);

}
