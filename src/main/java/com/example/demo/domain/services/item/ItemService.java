package com.example.demo.domain.services.item;

import com.example.demo.domain.dto.combos.item.*;
import com.example.demo.domain.enums.UnidadeMedida;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemBuscarService itemBuscarService;
    private final ItemCriarService itemCriarService;
    private final ItemValorService itemValorService;

    public ItemService(ItemBuscarService itemBuscarService, ItemCriarService itemCriarService, ItemValorService itemValorService) {
        this.itemBuscarService = itemBuscarService;
        this.itemCriarService = itemCriarService;
        this.itemValorService = itemValorService;
    }

    public Page<ItemDto>buscarItens(int pageNumber, String nome, UnidadeMedida unidadeMedida){
        return itemBuscarService.buscarItens(pageNumber, nome, unidadeMedida);
    }

    public List<ItemComboDto> buscarItensCombo(UUID comboId, UUID estruturaId){
        return itemBuscarService.buscarItensCombo(comboId, estruturaId);
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

    @Transactional
    public void inserirValor(InserirValorItemDto dto){
        itemValorService.inserirValor(dto);
    }
}
