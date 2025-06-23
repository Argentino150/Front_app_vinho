package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

// Implementa Serializable para podermos passar o objeto entre telas (para editar)
public class Cliente implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("sobrenome")
    private String sobrenome;

    @SerializedName("cnpjCpf")
    private String cnpjCpf;

    @SerializedName("email")
    private String email;

    @SerializedName("telefone")
    private String telefone;

    @SerializedName("endereco")
    private String endereco;

    // Getters e Setters para todos os campos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String cnpjCpf) { this.cnpjCpf = cnpjCpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}