package com.example.demo.domain.dto.combos.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemComboDto(
        UUID itemId,
        String itemNome,
        BigDecimal valor,
        UUID valorId
) {}
