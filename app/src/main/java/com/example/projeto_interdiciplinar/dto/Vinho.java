package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;

public class Vinho implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("safra")
    private int safra;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("notasDeDegustacao")
    private String notasDeDegustacao;

    @SerializedName("harmonizacoes")
    private String harmonizacoes;

    @SerializedName("imagemUrl")
    private String imagemUrl;

    @SerializedName("precoUnitario")
    private BigDecimal precoUnitario;

    // GETTERS (para ler os dados)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getSafra() { return safra; }
    public String getTipo() { return tipo; }
    public String getNotasDeDegustacao() { return notasDeDegustacao; }
    public String getHarmonizacoes() { return harmonizacoes; }
    public String getImagemUrl() { return imagemUrl; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }

    // SETTERS (para definir/alterar os dados) - A PARTE QUE FALTAVA
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSafra(int safra) { this.safra = safra; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setNotasDeDegustacao(String notasDeDegustacao) { this.notasDeDegustacao = notasDeDegustacao; }
    public void setHarmonizacoes(String harmonizacoes) { this.harmonizacoes = harmonizacoes; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}