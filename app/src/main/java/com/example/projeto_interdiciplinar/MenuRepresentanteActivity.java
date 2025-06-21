package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuRepresentanteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_representante);  // Altere para o seu layout de MenuRepresentante

        // Inicializando os botões
        Button btnVisitas = findViewById(R.id.btnVisitas);
        Button btnClientes = findViewById(R.id.btnClientes);
        Button btnVinhos = findViewById(R.id.btnVinhos);
        Button btnPedidos = findViewById(R.id.btnPedidos);
        Button btnMetas = findViewById(R.id.btnMetas);
        Button btnSair = findViewById(R.id.btnSair);

        // Ação do botão "Agenda visita"
        btnVisitas.setOnClickListener(v -> {
            startActivity(new Intent(this, AgendaVisitaActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Clientes"
        btnClientes.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientesActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Vinhos"
        btnVinhos.setOnClickListener(v -> {
            startActivity(new Intent(this, VinhosActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Pedidos"
        btnPedidos.setOnClickListener(v -> {
            startActivity(new Intent(this, PedidosActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Metas e Comissões"
        btnMetas.setOnClickListener(v -> {
            startActivity(new Intent(this, MetasComissoesActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Sair"
        btnSair.setOnClickListener(v -> {
            startActivity(new Intent(this, Menuinicial.class));  // Altere para a Activity correta
        });
    }
}
