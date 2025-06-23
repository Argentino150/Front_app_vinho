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

public class EditarClienteActivity extends AppCompatActivity {

    // Componentes da UI
    private EditText etNome, etSobrenome, etCpf, etEmail, etTelefone, etEndereco;
    private Button btnSalvar, btnVoltar;

    // Objeto que recebemos da tela anterior
    private Cliente clienteParaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        // Inicializa os componentes da tela
        initializeViews();

        // Pega o objeto Cliente que foi enviado pela ClientesActivity
        clienteParaEditar = (Cliente) getIntent().getSerializableExtra("CLIENTE_PARA_EDITAR");

        if (clienteParaEditar != null) {
            // Se o objeto existe, preenchemos os campos da tela com os dados dele
            populateFields();
        } else {
            // Se, por algum motivo, não recebemos o cliente, mostramos um erro e fechamos a tela
            Toast.makeText(this, "Erro: Não foi possível carregar os dados do cliente.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Define as ações dos botões
        btnSalvar.setOnClickListener(v -> saveChanges());
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

    private void populateFields() {
        etNome.setText(clienteParaEditar.getNome());
        etSobrenome.setText(clienteParaEditar.getSobrenome());
        etCpf.setText(clienteParaEditar.getCnpjCpf());
        etEmail.setText(clienteParaEditar.getEmail());
        etTelefone.setText(clienteParaEditar.getTelefone());
        etEndereco.setText(clienteParaEditar.getEndereco());
    }

    private void saveChanges() {
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

        // 3. Cria o objeto DTO para enviar à API (como o backend espera)
        // Para os campos não editáveis na tela, podemos passar null ou os valores antigos.
        // O ideal é que o backend ignore os campos nulos na atualização.
        ClienteCadastroDTO clienteAtualizadoDTO = new ClienteCadastroDTO(
                nome, sobrenome, cnpjCpf, email, telefone, endereco,
                null, null, null, null, null // Campos não presentes na tela de edição
        );

        // 4. Faz a chamada para a API para atualizar
        Call<Cliente> call = ApiClient.getApiService().updateCliente(clienteParaEditar.getId(), clienteAtualizadoDTO);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(@NonNull Call<Cliente> call, @NonNull Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarClienteActivity.this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Fecha a tela de edição e volta para a lista
                    finish();
                } else {
                    Toast.makeText(EditarClienteActivity.this, "Erro ao atualizar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Erro ao atualizar cliente: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cliente> call, @NonNull Throwable t) {
                Toast.makeText(EditarClienteActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_FAILURE", "Falha na comunicação ao atualizar cliente.", t);
            }
        });
    }
}