package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Pedido implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("cliente")
    private Cliente cliente; // Objeto Cliente aninhado

    @SerializedName("itens")
    private List<ItemPedido> itens; // Lista de Itens

    @SerializedName("dataDoPedido")
    private String dataDoPedido; // Tratado como String para simplicidade

    @SerializedName("condicoesDePagamento")
    private String condicoesDePagamento;

    @SerializedName("status")
    private String status;

    @SerializedName("valorTotal")
    private BigDecimal valorTotal;

    // Getters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public String getDataDoPedido() { return dataDoPedido; }
    public String getCondicoesDePagamento() { return condicoesDePagamento; }
    public String getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }
}