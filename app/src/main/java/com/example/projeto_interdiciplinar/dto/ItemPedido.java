package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("vinho")
    private Vinho vinho; // Objeto Vinho aninhado

    @SerializedName("quantidade")
    private int quantidade;

    @SerializedName("precoUnitario")
    private BigDecimal precoUnitario;

    // Getters
    public Long getId() { return id; }
    public Vinho getVinho() { return vinho; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
}