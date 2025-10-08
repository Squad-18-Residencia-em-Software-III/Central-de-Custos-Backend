package com.example.demo.domain.services.combo;

import com.example.demo.domain.dto.combos.ComboDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ComboService {

    private final ComboBuscaService comboBuscaService;

    public ComboService(ComboBuscaService comboBuscaService) {
        this.comboBuscaService = comboBuscaService;
    }

    public Page<ComboDto> buscarCombos(int pageNumber, UUID competenciaId, UUID estruturaId, String nome) {
        return comboBuscaService.buscarCombos(pageNumber, competenciaId, estruturaId, nome);
    }

}
