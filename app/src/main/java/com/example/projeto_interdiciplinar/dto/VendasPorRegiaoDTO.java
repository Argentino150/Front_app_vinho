package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class VendasPorRegiaoDTO {

    @SerializedName("regiao")
    private String regiao;

    @SerializedName("totalVendas")
    private BigDecimal totalVendas;

    // Getters
    public String getRegiao() { return regiao; }
    public BigDecimal getTotalVendas() { return totalVendas; }
}