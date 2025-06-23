package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Visita implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("dataHora")
    private String dataHora; // Como String para simplicidade
    @SerializedName("status")
    private String status;
    @SerializedName("observacoes")
    private String observacoes;
    @SerializedName("cliente")
    private Cliente cliente;
    @SerializedName("representante")
    private Representante representante;

    // Getters
    public Long getId() { return id; }
    public String getDataHora() { return dataHora; }
    public String getStatus() { return status; }
    public String getObservacoes() { return observacoes; }
    public Cliente getCliente() { return cliente; }
    public Representante getRepresentante() { return representante; }
}