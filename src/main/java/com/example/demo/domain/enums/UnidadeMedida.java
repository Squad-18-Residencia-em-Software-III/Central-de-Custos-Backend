package com.example.demo.domain.enums;

public enum UnidadeMedida {

    // --- MASSA ---
    GRAMA("g", "Grama"),
    QUILOGRAMA("kg", "Quilograma"),
    MILIGRAMA("mg", "Miligrama"),
    TONELADA("t", "Tonelada"),

    // --- VOLUME ---
    MILILITRO("mL", "Mililitro"),
    LITRO("L", "Litro"),
    METRO_CUBICO("m³", "Metro cúbico"),

    // --- COMPRIMENTO ---
    MILIMETRO("mm", "Milímetro"),
    CENTIMETRO("cm", "Centímetro"),
    METRO("m", "Metro"),
    QUILOMETRO("km", "Quilômetro"),

    // --- ÁREA ---
    CENTIMETRO_QUADRADO("cm²", "Centímetro quadrado"),
    METRO_QUADRADO("m²", "Metro quadrado"),
    HECTARE("ha", "Hectare"),
    QUILOMETRO_QUADRADO("km²", "Quilômetro quadrado"),

    // --- OUTRAS ---
    UNIDADE("un", "Unidade"),
    PORCENTAGEM("%", "Porcentagem"),
    PACOTE("pct", "Pacote"),
    CAIXA("cx", "Caixa"),
    PAR("par", "Par"),
    ROLO("rl", "Rolo");

    private final String simbolo;
    private final String descricao;

    UnidadeMedida(String simbolo, String descricao) {
        this.simbolo = simbolo;
        this.descricao = descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getDescricao() {
        return descricao;
    }
}
