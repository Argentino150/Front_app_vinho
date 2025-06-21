package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRepresentanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_representante);

        EditText edtUsuario = findViewById(R.id.edtUsuario);
        EditText edtSenha = findViewById(R.id.edtSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);
        Button btnSair = findViewById(R.id.btnSair);



        btnEntrar.setOnClickListener(v -> {
            String usuario = edtUsuario.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (usuario.equals("rep") && senha.equals("rep123")) {
                Intent intent = new Intent(this, MenuRepresentanteActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getString(R.string.login_falhou), Toast.LENGTH_SHORT).show();
            }
        });

        txtEsqueciSenha.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRepresentanteActivity.this, EsqueciSenhaActivity.class);
            intent.putExtra("isAdmin", false);
            startActivity(intent);
            finish();
        });

        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRepresentanteActivity.this, Menuinicial.class);
            startActivity(intent);
        });

    }
}
