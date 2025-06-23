package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VisitaPage {
    @SerializedName("content")
    private List<Visita> content;

    public List<Visita> getContent() {
        return content;
    }
}