package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_admin);

        // Botões
        Button btnVinhos = findViewById(R.id.btnVinhos);
        Button btnClientes = findViewById(R.id.btnClientes);
        Button btnVendedores = findViewById(R.id.btnVendedores);
        Button btnPedidos = findViewById(R.id.btnPedidos);
        Button btnRelatorios = findViewById(R.id.btnRelatorios);
        Button btnSair = findViewById(R.id.btnSair);

        // Ação do botão "Vinhos"
        btnVinhos.setOnClickListener(v -> {
            startActivity(new Intent(this, VinhosActivity.class));
        });

        // Ação do botão "Clientes"
        btnClientes.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientesActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Representantes"
        btnVendedores.setOnClickListener(v -> {
            startActivity(new Intent(this, RepresentantesActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Pedidos"
        btnPedidos.setOnClickListener(v -> {
            startActivity(new Intent(this, PedidosActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Relatórios"
        btnRelatorios.setOnClickListener(v -> {
            startActivity(new Intent(this, RelatoriosActivity.class));  // Altere para a Activity correta
        });

        // Ação do botão "Sair"
        btnSair.setOnClickListener(v -> {
            startActivity(new Intent(this, Menuinicial.class));  // Altere para a Activity correta
        });
    }
}
