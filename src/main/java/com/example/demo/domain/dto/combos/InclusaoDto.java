package com.example.demo.domain.dto.combos;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record InclusaoDto(
        @NotNull
        List<UUID> ids
) {
}
