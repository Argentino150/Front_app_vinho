package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SobreNosActivity extends AppCompatActivity {

    Button btnNossaHistoria, btnMissao, btnDiferencial, btnLocalizacao,btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sobre_nos);

        btnNossaHistoria = findViewById(R.id.btnNossaHistoria);
        btnMissao = findViewById(R.id.btnMissao);
        btnDiferencial = findViewById(R.id.btnDiferencial);
        btnLocalizacao = findViewById(R.id.btnLocalizacao);
        btnSair = findViewById(R.id.btnSair);

        btnNossaHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SobreNosActivity.this, NossaHistoriaActivity.class);
                startActivity(intent);
            }
        });

        btnMissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SobreNosActivity.this, MissaoVisaoActivity.class);
                startActivity(intent);
            }
        });

        btnDiferencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SobreNosActivity.this, DiferencialActivity.class);
                startActivity(intent);
            }
        });

        btnLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SobreNosActivity.this, LocalizacaoActivity.class);
                startActivity(intent);
            }
        });
        // Ação do botão "Sair"

        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(SobreNosActivity.this, Menuinicial.class);
            startActivity(intent);
        });

    }
}
