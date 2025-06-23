// O package foi atualizado
package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String token;

    // Getters e Setters (importantes para o Gson preencher os dados)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}