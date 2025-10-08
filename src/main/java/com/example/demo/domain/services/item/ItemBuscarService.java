package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.ItemDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.mapper.ItemMapper;
import com.example.demo.domain.repositorios.ItemRepository;
import com.example.demo.domain.repositorios.specs.ComboSpecs;
import com.example.demo.domain.repositorios.specs.ItemSpecs;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.ItemValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemBuscarService {

    private final ItemRepository itemRepository;
    private final ComboValidator comboValidator;
    private final ItemValidator itemValidator;
    private final ItemMapper itemMapper;

    public ItemBuscarService(ItemRepository itemRepository, ComboValidator comboValidator, ItemValidator itemValidator, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.comboValidator = comboValidator;
        this.itemValidator = itemValidator;
        this.itemMapper = itemMapper;
    }

    public List<ItemDto> buscarItensCombo(UUID comboId){
        Usuario usuario = AuthenticatedUserProvider.getAuthenticatedUser();
        Combo combo = comboValidator.validarComboExiste(comboId);
        comboValidator.validarAcessoBuscarCombos(usuario, combo.getEstrutura());

        return combo.getItens().stream().map(itemMapper::toDto).toList();
    }

    public Page<ItemDto> buscarItens(int pageNumber, String nome){
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);

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
