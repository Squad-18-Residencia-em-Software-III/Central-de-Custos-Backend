package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.folhaPagamento.FolhaPagamentoDto;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FolhaPagamentoMapper {

    @Mapping(target = "id", source = "uuid")
    FolhaPagamentoDto toDto(FolhaPagamento folhaPagamento);

}
