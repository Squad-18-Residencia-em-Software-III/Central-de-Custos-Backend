package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.CriarItemDto;
import com.example.demo.domain.dto.combos.item.EditarItemDto;
import com.example.demo.domain.dto.combos.item.ItemComboDto;
import com.example.demo.domain.dto.combos.item.ItemDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemBuscarService itemBuscarService;
    private final ItemCriarService itemCriarService;

    public ItemService(ItemBuscarService itemBuscarService, ItemCriarService itemCriarService) {
        this.itemBuscarService = itemBuscarService;
        this.itemCriarService = itemCriarService;
    }

    public Page<ItemDto> buscarItens(int pageNumber, String nome){
        return itemBuscarService.buscarItens(pageNumber, nome);
    }

    public List<ItemComboDto> buscarItensCombo(UUID comboId, UUID estruturaId, UUID competenciaId){
        return itemBuscarService.buscarItensCombo(comboId, estruturaId, competenciaId);
    }

    public ItemDto buscarItem(UUID itemId){
        return itemBuscarService.buscarItem(itemId);
    }

    @Transactional
    public void criarItem(CriarItemDto dto){
        itemCriarService.criarItem(dto);
    }

    @Transactional
    public void deletarItem(UUID itemId){
        itemCriarService.deletarItem(itemId);
    }

    @Transactional
    public void editarItem(UUID itemId, EditarItemDto dto){
        itemCriarService.editarItem(itemId, dto);
    }
}
