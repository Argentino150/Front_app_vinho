package com.example.projeto_interdiciplinar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_interdiciplinar.dto.Vinho;
import com.example.projeto_interdiciplinar.network.ApiClient;

import java.math.BigDecimal;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarVinhoActivity extends AppCompatActivity {

    private EditText etNome, etSafra, etTipo, etDescricao, etValor;
    private Button btnSalvar, btnVoltar;
    private Vinho vinhoParaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vinho);

        // Referências dos componentes da UI
        etNome = findViewById(R.id.et_nome);
        etSafra = findViewById(R.id.et_safra);
        etTipo = findViewById(R.id.et_tipo);
        etDescricao = findViewById(R.id.et_descricao); // Mapeado para notas de degustação
        etValor = findViewById(R.id.et_valor);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnVoltar = findViewById(R.id.btn_voltar);

        // Recebe o objeto Vinho da tela anterior
        vinhoParaEditar = (Vinho) getIntent().getSerializableExtra("VINHO_PARA_EDITAR");

        if (vinhoParaEditar != null) {
            populateFields();
        } else {
            Toast.makeText(this, "Erro: Vinho não encontrado.", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a tela se não houver vinho para editar
        }

        btnSalvar.setOnClickListener(v -> saveChanges());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void populateFields() {
        etNome.setText(vinhoParaEditar.getNome());
        etSafra.setText(String.valueOf(vinhoParaEditar.getSafra()));
        etTipo.setText(vinhoParaEditar.getTipo());
        etDescricao.setText(vinhoParaEditar.getNotasDeDegustacao());

        if (vinhoParaEditar.getPrecoUnitario() != null) {
            // Formata o BigDecimal para uma String simples para o EditText
            etValor.setText(vinhoParaEditar.getPrecoUnitario().toPlainString());
        }
    }

    private void saveChanges() {
        // Coleta os dados dos campos
        String nome = etNome.getText().toString().trim();
        String safraStr = etSafra.getText().toString().trim();
        String tipo = etTipo.getText().toString().trim();
        String descricao = etDescricao.getText().toString().trim();
        String valorStr = etValor.getText().toString().trim();

        // Validação simples
        if (nome.isEmpty() || safraStr.isEmpty() || tipo.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria um novo objeto Vinho com os dados atualizados
        Vinho vinhoAtualizado = new Vinho();
        // Não precisamos setar o ID no corpo, mas o backend não se importa. Os outros campos sim.
        vinhoAtualizado.setNome(nome);
        vinhoAtualizado.setSafra(Integer.parseInt(safraStr));
        vinhoAtualizado.setTipo(tipo);
        vinhoAtualizado.setNotasDeDegustacao(descricao);
        vinhoAtualizado.setPrecoUnitario(new BigDecimal(valorStr));
        // Os campos que não estão na tela de edição, como harmonizacoes e imagemUrl, não serão alterados
        // se o seu backend estiver configurado para ignorar valores nulos na atualização.

        // Faz a chamada para a API
        Call<Vinho> call = ApiClient.getApiService().updateVinho(vinhoParaEditar.getId(), vinhoAtualizado);
        call.enqueue(new Callback<Vinho>() {
            @Override
            public void onResponse(@NonNull Call<Vinho> call, @NonNull Response<Vinho> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarVinhoActivity.this, "Vinho atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela de edição e volta para a lista
                } else {
                    Toast.makeText(EditarVinhoActivity.this, "Erro ao atualizar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Erro ao atualizar vinho: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Vinho> call, @NonNull Throwable t) {
                Toast.makeText(EditarVinhoActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_FAILURE", "Falha na comunicação ao atualizar vinho.", t);
            }
        });
    }
}