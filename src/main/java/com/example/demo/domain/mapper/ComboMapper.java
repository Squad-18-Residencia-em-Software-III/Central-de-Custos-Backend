package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.entities.combos.Combo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComboMapper {

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "competencia", source = "competencia.competencia")
    ComboDto toDto(Combo combo);

}
