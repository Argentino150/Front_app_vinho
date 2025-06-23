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
import com.example.projeto_interdiciplinar.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarRepresentanteActivity extends AppCompatActivity {

    private EditText edtNome, edtSobrenome, edtEmail, edtTelefone, edtRegiao;
    private Spinner spinnerStatus;
    private Button btnSalvar, btnVoltar;

    private Representante representanteParaEditar;
    private ArrayAdapter<String> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_representante);

        initializeViews();
        setupStatusSpinner();

        representanteParaEditar = (Representante) getIntent().getSerializableExtra("REPRESENTANTE_PARA_EDITAR");

        if (representanteParaEditar != null) {
            populateFields();
        } else {
            Toast.makeText(this, "Erro ao carregar representante.", Toast.LENGTH_LONG).show();
            finish();
        }

        btnSalvar.setOnClickListener(v -> saveChanges());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        edtNome = findViewById(R.id.edtNome);
        edtSobrenome = findViewById(R.id.edtSobrenome);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtRegiao = findViewById(R.id.edtRegiao);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void setupStatusSpinner() {
        String[] statusOptions = {"ATIVO", "INATIVO"};
        statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
    }

    private void populateFields() {
        edtNome.setText(representanteParaEditar.getNome());
        edtSobrenome.setText(representanteParaEditar.getSobrenome());
        edtEmail.setText(representanteParaEditar.getEmail());
        edtTelefone.setText(representanteParaEditar.getTelefone());
        edtRegiao.setText(representanteParaEditar.getRegiao());

        // Seleciona o status correto no Spinner
        if (representanteParaEditar.getStatus() != null) {
            int spinnerPosition = statusAdapter.getPosition(representanteParaEditar.getStatus());
            spinnerStatus.setSelection(spinnerPosition);
        }
    }

    private void saveChanges() {
        // 1. Coleta os dados atualizados
        String nome = edtNome.getText().toString().trim();
        String sobrenome = edtSobrenome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String regiao = edtRegiao.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        // 2. Validação
        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nome e e-mail são obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Atualiza o objeto que recebemos com os novos dados
        // O backend para o PUT de representante espera o objeto Representante inteiro
        representanteParaEditar.setNome(nome);
        representanteParaEditar.setSobrenome(sobrenome);
        representanteParaEditar.setEmail(email);
        representanteParaEditar.setTelefone(telefone);
        representanteParaEditar.setRegiao(regiao);
        representanteParaEditar.setStatus(status);

        // 4. Faz a chamada da API
        Call<Representante> call = ApiClient.getApiService().updateRepresentante(representanteParaEditar.getId(), representanteParaEditar);
        call.enqueue(new Callback<Representante>() {
            @Override
            public void onResponse(@NonNull Call<Representante> call, @NonNull Response<Representante> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarRepresentanteActivity.this, "Representante atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela e volta para a lista
                } else {
                    Toast.makeText(EditarRepresentanteActivity.this, "Erro ao atualizar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Representante> call, @NonNull Throwable t) {
                Toast.makeText(EditarRepresentanteActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}