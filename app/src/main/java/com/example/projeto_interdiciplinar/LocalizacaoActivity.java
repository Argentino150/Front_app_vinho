package com.example.projeto_interdiciplinar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LocalizacaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        //anim fade
        View layout = findViewById(R.id.layout_principal);
        layout.setAlpha(0f);
        layout.animate().alpha(1f).setDuration(800);  // Faz o fade em 800ms

        ImageView voltarButton = findViewById(R.id.lista_voltar);
        voltarButton.setOnClickListener(v -> {
            Intent intent = new Intent(LocalizacaoActivity.this, SobreNosActivity.class);  // Corrigido para a atividade correta
            startActivity(intent);
            finish();
        });

    }
}