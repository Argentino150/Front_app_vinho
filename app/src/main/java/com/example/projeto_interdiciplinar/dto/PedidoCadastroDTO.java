package com.example.projeto_interdiciplinar.dto;

import java.util.List;

public class PedidoCadastroDTO {
    private Long clienteId;
    private Long representanteId;
    private String condicoesDePagamento;
    private List<ItemPedidoDTO> itens;

    public PedidoCadastroDTO(Long clienteId, Long representanteId, String condicoesDePagamento, List<ItemPedidoDTO> itens) {
        this.clienteId = clienteId;
        this.representanteId = representanteId;
        this.condicoesDePagamento = condicoesDePagamento;
        this.itens = itens;
    }
}