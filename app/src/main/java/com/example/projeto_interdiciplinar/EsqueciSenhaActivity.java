package com.example.projeto_interdiciplinar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        //anim fade
        View layout = findViewById(R.id.layout_principal);
        layout.setAlpha(0f);
        layout.animate().alpha(1f).setDuration(800);  // Faz o fade em 800ms

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            isAdmin = receivedIntent.getBooleanExtra("isAdmin", false);
        }

        Button btnVoltaLogin = findViewById(R.id.btnVoltaLogin);
        btnVoltaLogin.setOnClickListener(v -> {
            if(isAdmin){
                Intent intent = new Intent(EsqueciSenhaActivity.this, LoginAdminActivity.class);  // Corrigido para a atividade correta
                startActivity(intent);
            }else{
                Intent intent = new Intent(EsqueciSenhaActivity.this, LoginRepresentanteActivity.class);
                startActivity(intent);
            }

            finish();
        });

    }
}