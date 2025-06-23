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

import com.example.projeto_interdiciplinar.dto.Vinho;
import com.example.projeto_interdiciplinar.network.ApiClient;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroVinhoActivity extends AppCompatActivity {

    // Componentes da UI
    private EditText etNome, etSafra, etDescricao, etValor, etImagemUrl;
    private Spinner spinnerTipo;
    private Button btnSalvar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Garanta que o nome do layout está correto no seu projeto.
        // Se o arquivo se chama activity_cadastro_vinhos.xml, mude aqui para R.layout.activity_cadastro_vinhos
        setContentView(R.layout.activity_cadastro_vinho);

        // Inicializa os componentes
        initializeViews();
        // Configura as opções do Spinner
        setupSpinner();

        // Define as ações dos botões
        btnSalvar.setOnClickListener(v -> createVinho());
        btnVoltar = findViewById(R.id.btn_voltar); // Corrigido para o ID do seu XML de vinhos
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        etNome = findViewById(R.id.et_nome);
        etSafra = findViewById(R.id.et_safra);
        etDescricao = findViewById(R.id.et_descricao);
        etValor = findViewById(R.id.et_valor);
        spinnerTipo = findViewById(R.id.spinner_tipo);
        btnSalvar = findViewById(R.id.btn_salvar);
        etImagemUrl = findViewById(R.id.et_imagem_url); // <-- ADICIONE ESTA LINHA
    }

    private void setupSpinner() {
        // Cria uma lista de opções para o Spinner
        String[] tiposDeVinho = new String[]{"Tinto", "Branco", "Rosé", "Espumante", "Doce"};
        // Cria um Adapter para o Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposDeVinho);
        // Define o layout do dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Seta o adapter no Spinner
        spinnerTipo.setAdapter(adapter);
    }

    private void createVinho() {
        // 1. Coleta os dados dos campos
        String nome = etNome.getText().toString().trim();
        String safraStr = etSafra.getText().toString().trim();
        String tipo = spinnerTipo.getSelectedItem().toString();
        String descricao = etDescricao.getText().toString().trim();
        String valorStr = etValor.getText().toString().trim();
        String imagemUrl = etImagemUrl.getText().toString().trim(); // <-- Pega a URL da imagem

        // 2. Validação simples
        if (nome.isEmpty() || safraStr.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Nome, safra e valor são obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Cria o objeto Vinho para enviar à API
        Vinho novoVinho = new Vinho();
        novoVinho.setNome(nome);
        novoVinho.setSafra(Integer.parseInt(safraStr));
        novoVinho.setTipo(tipo);
        novoVinho.setNotasDeDegustacao(descricao);
        novoVinho.setPrecoUnitario(new BigDecimal(valorStr));
        novoVinho.setHarmonizacoes(null);
        novoVinho.setImagemUrl(imagemUrl); // <-- Usa a URL da imagem aqui

        // 4. Faz a chamada para a API
        Call<Vinho> call = ApiClient.getApiService().createVinho(novoVinho);
        call.enqueue(new Callback<Vinho>() {
            @Override
            public void onResponse(@NonNull Call<Vinho> call, @NonNull Response<Vinho> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroVinhoActivity.this, "Vinho cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela e volta para a lista
                } else {
                    Toast.makeText(CadastroVinhoActivity.this, "Erro ao cadastrar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Erro ao cadastrar vinho: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Vinho> call, @NonNull Throwable t) {
                Toast.makeText(CadastroVinhoActivity.this, "Falha na conexão.", Toast.LENGTH_SHORT).show();
                Log.e("API_FAILURE", "Falha na comunicação ao cadastrar vinho.", t);
            }
        });
    }
}