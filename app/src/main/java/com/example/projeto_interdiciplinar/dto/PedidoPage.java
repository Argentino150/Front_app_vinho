package com.example.projeto_interdiciplinar.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PedidoPage {
    @SerializedName("content")
    private List<Pedido> content;

    public List<Pedido> getContent() {
        return content;
    }
}