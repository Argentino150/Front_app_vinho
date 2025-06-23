package com.example.projeto_interdiciplinar.dto;

import java.io.Serializable; // NOVO IMPORT

// Usado para criar um pedido
public class ItemPedidoDTO implements Serializable { // IMPLEMENTS ADICIONADO
    private Long vinhoId;
    private int quantidade;

    public ItemPedidoDTO(Long vinhoId, int quantidade) {
        this.vinhoId = vinhoId;
        this.quantidade = quantidade;
    }

    // Getters para o Gson usar
    public Long getVinhoId() { return vinhoId; }
    public int getQuantidade() { return quantidade; }
}