package com.example.demo.domain.services.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import com.example.demo.domain.dto.combos.CriarComboDto;
import com.example.demo.domain.dto.combos.EditarComboDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ComboService {

    private final ComboBuscaService comboBuscaService;
    private final ComboCriarService comboCriarService;

    public ComboService(ComboBuscaService comboBuscaService, ComboCriarService comboCriarService) {
        this.comboBuscaService = comboBuscaService;
        this.comboCriarService = comboCriarService;
    }

    public List<ComboDto> buscarCombosEstrutura(UUID estruturaId, String nome) {
        return comboBuscaService.buscarCombosEstrutura(estruturaId, nome);
    }

    public Page<ComboDto> buscarCombos(int pageNumber, String nome) {
        return comboBuscaService.buscarCombos(pageNumber, nome);
    }

    @Transactional
    public void criarCombo(CriarComboDto dto){
        comboCriarService.criarCombo(dto);
    }

    @Transactional
    public void adicionarEstruturaAoCombo(UUID comboId, UUID estruturaId) {
        comboCriarService.adicionarEstruturaAoCombo(comboId, estruturaId);
    }

    @Transactional
    public void adicionarItemAoCombo(UUID comboId, UUID itemId) {
        comboCriarService.adicionarItemAoCombo(comboId, itemId);
    }

    @Transactional
    public void removerItemDoCombo(UUID comboId, UUID itemId) {
        comboCriarService.removerItemDoCombo(comboId, itemId);
    }

    @Transactional
    public void removerEstruturaDoCombo(UUID comboId, UUID estruturaId) {
        comboCriarService.removerEstruturaDoCombo(comboId, estruturaId);
    }

    @Transactional
    public void editarCombo(UUID comboId, EditarComboDto dto){
        comboCriarService.editarCombo(comboId, dto);
    }

}
