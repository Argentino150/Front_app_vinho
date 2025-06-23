package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RepresentantePage {

    @SerializedName("content")
    private List<Representante> content;

    public List<Representante> getContent() {
        return content;
    }
}