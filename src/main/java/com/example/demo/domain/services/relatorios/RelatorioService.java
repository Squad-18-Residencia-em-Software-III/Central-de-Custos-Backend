package com.example.demo.domain.services.relatorios;

import com.example.demo.domain.dto.relatorios.graficos.GastosTotaisCompetenciaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.repositorios.ValorItemComboRepository;
import com.example.demo.domain.validations.EstruturaValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class RelatorioService {

    private final ValorItemComboRepository valorItemComboRepository;
    private final EstruturaValidator estruturaValidator;

    public RelatorioService(ValorItemComboRepository valorItemComboRepository, EstruturaValidator estruturaValidator) {
        this.valorItemComboRepository = valorItemComboRepository;
        this.estruturaValidator = estruturaValidator;
    }

    public List<GastosTotaisCompetenciaDto> buscarGatosTotaisPorCompetencia(UUID estruturaId, int ano){
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(estruturaId);
        List<Object[]> resultados = valorItemComboRepository.gastosTotaisPorCompetenciaAno(estrutura.getId(), ano);

        return resultados.stream()
                .map(obj -> new GastosTotaisCompetenciaDto(
                        (String) obj[0],
                        (BigDecimal) obj[1]
                ))
                .toList();
    }

}
