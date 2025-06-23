package com.example.projeto_interdiciplinar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ClienteCadastroDTO;
import com.example.projeto_interdiciplinar.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroClienteActivity extends AppCompatActivity {

    // Componentes da UI
    private EditText etNome, etSobrenome, etCpf, etEmail, etTelefone, etEndereco;
    private Button btnSalvar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        // Inicializa os componentes da tela
        initializeViews();

        // Define as ações dos botões
        btnSalvar.setOnClickListener(v -> createClient());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        etNome = findViewById(R.id.et_nome);
        etSobrenome = findViewById(R.id.et_sobrenome);
        etCpf = findViewById(R.id.et_cpf);
        etEmail = findViewById(R.id.et_email);
        etTelefone = findViewById(R.id.et_telefone);
        etEndereco = findViewById(R.id.et_endereco);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void createClient() {
        // 1. Coleta os dados dos campos
        String nome = etNome.getText().toString().trim();
        String sobrenome = etSobrenome.getText().toString().trim();
        String cnpjCpf = etCpf.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String endereco = etEndereco.getText().toString().trim();

        // 2. Validação simples
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Nome, e-mail e telefone são obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Cria o objeto DTO para enviar à API
        // TODO: A forma de obter o representanteId deve ser melhorada no futuro,
        // por exemplo, usando um Spinner para selecionar um representante da lista.
        // Por enquanto, vamos usar um valor fixo (1L) para teste.
        // Garanta que existe um representante com ID 1 no seu banco de dados.
        Long representanteId = 1L;

        ClienteCadastroDTO novoClienteDTO = new ClienteCadastroDTO(
                nome, sobrenome, cnpjCpf, email, telefone, endereco,
                null, null, null, null, // Campos não presentes na tela (responsavel, contatos, lat, long)
                representanteId
        );

        // 4. Faz a chamada para a API para criar o novo cliente
        Call<Cliente> call = ApiClient.getApiService().createCliente(novoClienteDTO);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroClienteActivity.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Fecha a tela de cadastro e volta para a lista,
                    // que será atualizada automaticamente pelo onResume()
                    finish();
                } else {
                    Toast.makeText(CadastroClienteActivity.this, "Erro ao cadastrar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Erro ao cadastrar cliente: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {
                Toast.makeText(CadastroClienteActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_FAILURE", "Falha na comunicação ao cadastrar cliente.", t);
            }
        });
    }
}