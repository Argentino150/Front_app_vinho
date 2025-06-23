package com.example.projeto_interdiciplinar.dto;

public class VisitaCadastroDTO {
    private Long clienteId;
    private Long representanteId;
    private String dataHora; // Enviamos como String no formato "AAAA-MM-DDTHH:MM:SS"
    private String observacoes;
    private String status;

    public VisitaCadastroDTO(Long clienteId, Long representanteId, String dataHora, String observacoes, String status) {
        this.clienteId = clienteId;
        this.representanteId = representanteId;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
        this.status = status;
    }
}