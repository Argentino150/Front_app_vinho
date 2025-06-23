package com.example.projeto_interdiciplinar.dto;

// Usamos uma classe normal em vez de um record para facilitar a construção do objeto no Android
public class ClienteCadastroDTO {
    private String nome;
    private String sobrenome;
    private String cnpjCpf;
    private String email;
    private String telefone;
    private String endereco;
    private String responsavel;
    private String contatos;
    private Double latitude;
    private Double longitude;
    private Long representanteId;

    // Construtor usado para criar ou atualizar um cliente
    public ClienteCadastroDTO(String nome, String sobrenome, String cnpjCpf, String email, String telefone, String endereco, String responsavel, String contatos, Double latitude, Double longitude, Long representanteId) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cnpjCpf = cnpjCpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.contatos = contatos;
        this.latitude = latitude;
        this.longitude = longitude;
        this.representanteId = representanteId;
    }
}