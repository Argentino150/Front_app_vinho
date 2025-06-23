package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_interdiciplinar.network.ApiClient;
import com.example.projeto_interdiciplinar.dto.LoginRequest;
import com.example.projeto_interdiciplinar.dto.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnSair = findViewById(R.id.btnSair);
        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);

        btnEntrar.setOnClickListener(v -> {
            // Chamamos nosso novo método de login
            realizarLogin();
        });

        txtEsqueciSenha.setOnClickListener(v -> {
            Intent intent = new Intent(LoginAdminActivity.this, EsqueciSenhaActivity.class);
            intent.putExtra("isAdmin", true); // define que é admin
            startActivity(intent);
            finish();
        });

        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(LoginAdminActivity.this, Menuinicial.class);
            startActivity(intent);
        });
    }

    private void realizarLogin() {
        String email = edtUsuario.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        // 1. Validação simples para não enviar campos vazios
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o email e a senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Criar o objeto de requisição com os dados digitados
        LoginRequest loginRequest = new LoginRequest(email, senha);

        // 3. Obter o serviço da API e criar a chamada
        Call<LoginResponse> call = ApiClient.getApiService().login(loginRequest);

        // 4. Executar a chamada de forma assíncrona (em outra thread)
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                // O servidor respondeu (seja com sucesso ou com erro)

                if (response.isSuccessful() && response.body() != null) {
                    // SUCESSO! HTTP 2xx e corpo da resposta não é nulo.
                    String token = response.body().getToken();
                    Log.i("API_SUCCESS", "Login bem-sucedido! Token: " + token);
                    Toast.makeText(LoginAdminActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                    // TODO: Salvar o token em SharedPreferences para usar em outras telas.

                    // Navega para a próxima tela
                    Intent intent = new Intent(LoginAdminActivity.this, MenuAdminActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // FALHA! O servidor respondeu com um erro (Ex: 401 - Não autorizado, 404, 500)
                    Log.e("API_ERROR", "Erro no login. Código: " + response.code());
                    Toast.makeText(LoginAdminActivity.this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                // FALHA NA COMUNICAÇÃO! Não foi possível nem falar com o servidor.
                // (Ex: sem internet, URL errada, servidor offline)
                Log.e("API_FAILURE", "Falha na comunicação com a API.", t);
                Toast.makeText(LoginAdminActivity.this, "Não foi possível conectar ao servidor. Verifique sua conexão.", Toast.LENGTH_LONG).show();
            }
        });
    }
}