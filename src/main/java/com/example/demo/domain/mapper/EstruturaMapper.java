package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.estrutura.EstruturaDto;
import com.example.demo.domain.dto.estrutura.EstruturaInfoDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstruturaMapper {

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "municipio", source = "municipio.nome")
    @Mapping(target = "uf", source = "municipio.uf.sigla")
    EstruturaDto toDto(Estrutura estrutura);

    @Mapping(target = "municipio", source = "municipio.nome")
    @Mapping(target = "uf", source = "municipio.uf.sigla")
    EstruturaInfoDto toInfoDto(Estrutura estrutura);

}
