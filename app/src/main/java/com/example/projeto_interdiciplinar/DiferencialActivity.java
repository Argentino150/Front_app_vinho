package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DiferencialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diferencial);

        ImageView voltarButton = findViewById(R.id.lista_voltar);
        voltarButton.setOnClickListener(v -> {
            Intent intent = new Intent(DiferencialActivity.this, SobreNosActivity.class);  // Corrigido para a atividade correta
            startActivity(intent);
            finish();
        });

    }
}