package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Menuinicial  extends AppCompatActivity {

    Button btnRepresentante, btnAdministrador,btnSobreNos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_inicial);

        btnRepresentante = findViewById(R.id.btnRepresentante);
        btnAdministrador = findViewById(R.id.btnAdministrador);
        btnSobreNos = findViewById(R.id.btnSobreNos);


        btnRepresentante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menuinicial.this, LoginRepresentanteActivity.class);
                startActivity(intent);
            }
        });

        btnAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menuinicial.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        btnSobreNos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menuinicial.this, SobreNosActivity.class);
                startActivity(intent);
            }
        });



    }
}
