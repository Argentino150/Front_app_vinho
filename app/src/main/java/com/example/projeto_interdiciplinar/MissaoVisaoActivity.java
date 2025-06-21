package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class MissaoVisaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        ImageView voltarButton = findViewById(R.id.lista_voltar);
        voltarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MissaoVisaoActivity.this, SobreNosActivity.class);  // Corrigido para a atividade correta
            startActivity(intent);
            finish();
        });


    }
}
