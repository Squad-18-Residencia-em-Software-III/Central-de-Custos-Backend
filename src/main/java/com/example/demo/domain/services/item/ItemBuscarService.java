package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.ItemComboDto;
import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.ItemMapper;
import com.example.demo.domain.repositorios.ItemRepository;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.repositorios.specs.ItemSpecs;
import com.example.demo.domain.repositorios.specs.ValorItemSpecs;
import com.example.demo.domain.services.competencia.CompetenciaService;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.ItemValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ItemBuscarService {

    private final ItemRepository itemRepository;
    private final ComboValidator comboValidator;
    private final ItemValidator itemValidator;
    private final ItemMapper itemMapper;
    private final EstruturaValidator estruturaValidator;
    private final CompetenciaService competenciaService;
    private final ValorItemComboRepository valorItemComboRepository;

    public ItemBuscarService(ItemRepository itemRepository, ComboValidator comboValidator, ItemValidator itemValidator, ItemMapper itemMapper, EstruturaValidator estruturaValidator, CompetenciaService competenciaService, ValorItemComboRepository valorItemComboRepository) {
        this.itemRepository = itemRepository;
        this.comboValidator = comboValidator;
        this.itemValidator = itemValidator;
        this.itemMapper = itemMapper;
        this.estruturaValidator = estruturaValidator;
        this.competenciaService = competenciaService;
        this.valorItemComboRepository = valorItemComboRepository;
    }

    public List<ItemComboDto> buscarItensCombo(UUID comboUuid, UUID estruturaUuid, UUID competenciaUuid) {
        Combo combo = comboValidator.validarComboExiste(comboUuid);
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaUuid);
        Competencia competencia = competenciaUuid != null
                ? comboValidator.validarCompetenciaExiste(competenciaUuid)
                : competenciaService.getCompetenciaAtual();

        List<Object[]> resultados = valorItemComboRepository.buscarItensDoComboComValores(
                estrutura.getId(),
                competencia.getId(),
                combo.getId()
        );

        return resultados.stream()
                .map(r -> new ItemComboDto(
                        (UUID) r[0],
                        (String) r[1],
                        (BigDecimal) r[2],
                        (UUID) r[3],
                        (UUID) r[4]
                ))
                .toList();
    }

    public Page<ItemDto> buscarItens(int pageNumber, String nome){
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);

        Specification<ItemCombo> spec = Specification.allOf();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(ItemSpecs.comNomeContendo(nome));
        }

        Page<ItemCombo> items = itemRepository.findAll(spec, pageable);

        return items.map(itemMapper::toDto);
    }

    public ItemDto buscarItem(UUID uuid){
        ItemCombo item = itemValidator.validaItemExiste(uuid);
        return itemMapper.toDto(item);
    }
}
