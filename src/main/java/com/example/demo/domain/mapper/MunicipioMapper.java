package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.municipio.MunicipioDto;
import com.example.demo.domain.entities.Municipio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipioMapper {

    @Mapping(target = "id", source = "uuid")
    MunicipioDto toDto(Municipio municipio);

}
