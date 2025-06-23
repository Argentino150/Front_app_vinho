package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VinhoPage {

    @SerializedName("content")
    private List<Vinho> content;

    public List<Vinho> getContent() {
        return content;
    }
}