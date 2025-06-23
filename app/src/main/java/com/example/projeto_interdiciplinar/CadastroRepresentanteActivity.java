package com.example.projeto_interdiciplinar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.Representante;
import com.example.projeto_interdiciplinar.dto.RepresentanteCadastroDTO;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.math.BigDecimal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroRepresentanteActivity extends AppCompatActivity {

    private EditText edtNome, edtSobrenome, edtEmail, edtTelefone, edtRegiao, edtSenha;
    private Spinner spinnerStatus;
    private Button btnSalvar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Verifique se o nome do layout está correto no seu projeto
        setContentView(R.layout.activity_cadastro_representante);

        initializeViews();
        setupStatusSpinner();

        btnSalvar.setOnClickListener(v -> createRepresentante());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        edtNome = findViewById(R.id.edtNome);
        edtSobrenome = findViewById(R.id.edtSobrenome);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtRegiao = findViewById(R.id.edtRegiao);
        edtSenha = findViewById(R.id.edtSenha); // O campo que adicionamos
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void setupStatusSpinner() {
        String[] statusOptions = {"ATIVO", "INATIVO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void createRepresentante() {
        // 1. Coleta os dados dos campos
        String nome = edtNome.getText().toString().trim();
        String sobrenome = edtSobrenome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String regiao = edtRegiao.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        // 2. Validação dos campos obrigatórios
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Nome, e-mail e senha são obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Cria o DTO para enviar para a API
        // Campos como meta e taxa de comissão não estão na tela, então enviamos null
        RepresentanteCadastroDTO novoRepresentante = new RepresentanteCadastroDTO(
                nome, sobrenome, email, telefone, regiao, status, senha,
                null, null, "USER" // Meta e Taxa nulos, Role padrão como USER
        );

        // 4. Faz a chamada para a API
        ApiClient.getApiService().createRepresentante(novoRepresentante).enqueue(new Callback<Representante>() {
            @Override
            public void onResponse(@NonNull Call<Representante> call, @NonNull Response<Representante> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroRepresentanteActivity.this, "Representante cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela e volta para a lista, que será atualizada
                } else {
                    Toast.makeText(CadastroRepresentanteActivity.this, "Erro ao cadastrar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Erro ao cadastrar representante: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Representante> call, @NonNull Throwable t) {
                Toast.makeText(CadastroRepresentanteActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_FAILURE", "Falha de comunicação", t);
            }
        });
    }
}