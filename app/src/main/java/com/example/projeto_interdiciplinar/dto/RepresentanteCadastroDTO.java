package com.example.projeto_interdiciplinar.dto;

import java.math.BigDecimal;

public class RepresentanteCadastroDTO {
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String regiao;
    private String status;
    private String senha;
    private BigDecimal meta;
    private BigDecimal taxaComissao;
    private String role;

    // Construtor completo para criar o objeto
    public RepresentanteCadastroDTO(String nome, String sobrenome, String email, String telefone, String regiao, String status, String senha, BigDecimal meta, BigDecimal taxaComissao, String role) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.telefone = telefone;
        this.regiao = regiao;
        this.status = status;
        this.senha = senha;
        this.meta = meta;
        this.taxaComissao = taxaComissao;
        this.role = role;
    }
}