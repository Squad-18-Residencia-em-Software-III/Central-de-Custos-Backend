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
    @Mapping(target = "competencia", ignore = true)
    Combo toEntity(CriarComboDto dto);

    @Mapping(source = "uuid", target = "id")
    @Mapping(target = "competencia", source = "competencia.competencia")
    ComboDetalhadoDto toDtoInfo(Combo combo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    Combo clonarCombo(Combo combo);


}
