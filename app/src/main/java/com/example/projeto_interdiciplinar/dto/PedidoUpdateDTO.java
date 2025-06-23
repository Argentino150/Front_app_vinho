package com.example.projeto_interdiciplinar.dto;

public class PedidoUpdateDTO {
    private String status;
    private String condicoesDePagamento;

    public PedidoUpdateDTO(String status, String condicoesDePagamento) {
        this.status = status;
        this.condicoesDePagamento = condicoesDePagamento;
    }
}