package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.CriarItemDto;
import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.mapper.ItemMapper;
import com.example.demo.domain.repositorios.ItemRepository;
import com.example.demo.domain.services.item.strategy.EditarCampoItemStrategy;
import com.example.demo.domain.validations.ItemValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemCriarService {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;
    private final ItemMapper itemMapper;
    private final List<EditarCampoItemStrategy> editarCampoItemStrategies;

    public ItemCriarService(ItemRepository itemRepository, ItemValidator itemValidator, ItemMapper itemMapper, List<EditarCampoItemStrategy> editarCampoItemStrategies) {
        this.itemRepository = itemRepository;
        this.itemValidator = itemValidator;
        this.itemMapper = itemMapper;
        this.editarCampoItemStrategies = editarCampoItemStrategies;
    }

    @Transactional
    public void criarItem(CriarItemDto dto){
        itemValidator.validaItemExisteNome(dto.nome());
        itemRepository.save(itemMapper.toEntity(dto));
    }

    @Transactional
    public void deletarItem(UUID itemId){
        ItemCombo item = itemValidator.validaItemExiste(itemId);
        itemValidator.validaCombosPossuemItem(item);
        itemRepository.delete(item);
    }

    @Transactional
    public void editarItem(UUID itemId, EditarItemDto dto){
        ItemCombo item = itemValidator.validaItemExiste(itemId);
        editarCampoItemStrategies.forEach(s -> s.editar(item, dto));
        itemRepository.save(item);
    }
}
