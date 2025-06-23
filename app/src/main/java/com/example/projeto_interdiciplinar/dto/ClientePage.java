package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ClientePage {

    @SerializedName("content")
    private List<Cliente> content;

    public List<Cliente> getContent() {
        return content;
    }
}