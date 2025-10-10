package com.example.demo.domain.dto.combos.item;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ItemComboDto {
    private final UUID uuid;
    private final String nome;
    private final BigDecimal valor;
    private final UUID valorUuid;

    public ItemComboDto(UUID uuid, String nome, BigDecimal valor, UUID valorUuid) {
        this.uuid = uuid;
        this.nome = nome;
        this.valor = valor;
        this.valorUuid = valorUuid;
    }

}

