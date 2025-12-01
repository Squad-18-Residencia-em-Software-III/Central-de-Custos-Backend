package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.competencia.CompetenciaDto;
import com.example.demo.domain.entities.competencia.Competencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetenciaMapper {

    @Mapping(target = "id", source = "uuid")
    CompetenciaDto toDto(Competencia competencia);

}
