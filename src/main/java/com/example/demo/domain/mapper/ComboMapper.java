package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.competencia.Competencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface ComboMapper {

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "estruturaNome", source = "estrutura.nome")
    @Mapping(target = "competencia", source = "competencia.competencia")
    ComboDto toDto(Combo combo);

}
