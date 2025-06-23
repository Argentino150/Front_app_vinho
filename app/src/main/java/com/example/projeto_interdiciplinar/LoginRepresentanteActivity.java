package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.LoginRequest;
import com.example.projeto_interdiciplinar.dto.LoginResponse;
import com.example.projeto_interdiciplinar.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepresentanteActivity extends AppCompatActivity {

    private EditText edtUsuario, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_representante);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);
        Button btnSair = findViewById(R.id.btnSair);

        btnEntrar.setOnClickListener(v -> {
            // A lógica de login agora chama nosso método que usa a API
            realizarLoginApi();
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

    private void realizarLoginApi() {
        String email = edtUsuario.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o e-mail e a senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Monta o corpo da requisição
        LoginRequest loginRequest = new LoginRequest(email, senha);

        // Faz a chamada para a API
        ApiClient.getApiService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // SUCESSO!
                    String token = response.body().getToken();
                    Log.i("LOGIN_SUCCESS", "Token recebido: " + token);
                    Toast.makeText(LoginRepresentanteActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                    // TODO: Salvar o token no SharedPreferences para manter o usuário logado

                    // Navega para o menu do representante
                    Intent intent = new Intent(LoginRepresentanteActivity.this, MenuRepresentanteActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // Falha de autenticação (usuário/senha errados, etc.)
                    Toast.makeText(LoginRepresentanteActivity.this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                // Falha de rede (sem internet, servidor offline, etc.)
                Toast.makeText(LoginRepresentanteActivity.this, "Falha na comunicação com o servidor.", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_FAILURE", "Erro na API de login", t);
            }
        });
    }
}