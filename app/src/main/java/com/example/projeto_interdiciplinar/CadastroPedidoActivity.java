package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ClientePage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroPedidoActivity extends AppCompatActivity {

    private Spinner spinnerClientes;
    private Spinner spinnerFormaPagamento;
    private Button btnProximo, btnVoltar;

    private List<Cliente> listaDeClientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido_cliente);

        initializeViews();
        setupFormaPagamentoSpinner();
        fetchClientesParaSpinner();

        btnProximo.setOnClickListener(v -> irParaAdicionarItens());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        spinnerClientes = findViewById(R.id.spinner_clientes);
        spinnerFormaPagamento = findViewById(R.id.spinner_forma_pagamento);
        btnProximo = findViewById(R.id.btn_proximo);
        btnVoltar = findViewById(R.id.btn_voltar);
    }

    private void setupFormaPagamentoSpinner() {
        String[] formas = {"Boleto 30/60 dias", "PIX na entrega", "Cartão de Crédito"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormaPagamento.setAdapter(adapter);
    }

    private void fetchClientesParaSpinner() {
        ApiClient.getApiService().getClientes().enqueue(new Callback<ClientePage>() {
            @Override
            public void onResponse(@NonNull Call<ClientePage> call, @NonNull Response<ClientePage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaDeClientes = response.body().getContent();
                    List<String> nomesClientes = new ArrayList<>();
                    for (Cliente cliente : listaDeClientes) {
                        nomesClientes.add(cliente.getNome());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CadastroPedidoActivity.this, android.R.layout.simple_spinner_item, nomesClientes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);
                } else {
                    Toast.makeText(CadastroPedidoActivity.this, "Falha ao carregar clientes.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClientePage> call, @NonNull Throwable t) {
                Toast.makeText(CadastroPedidoActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irParaAdicionarItens() {
        if (listaDeClientes.isEmpty()) {
            Toast.makeText(this, "Nenhum cliente selecionado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pega o cliente selecionado no Spinner
        int posicaoSelecionada = spinnerClientes.getSelectedItemPosition();
        Cliente clienteSelecionado = listaDeClientes.get(posicaoSelecionada);
        Long clienteId = clienteSelecionado.getId();

        // Pega a forma de pagamento selecionada
        String condPagamento = spinnerFormaPagamento.getSelectedItem().toString();

        // Abre a próxima tela, passando os dados
        Intent intent = new Intent(this, CadastroPedidoitemActivity.class);
        intent.putExtra("CLIENTE_ID", clienteId);
        intent.putExtra("CONDICOES_PAGAMENTO", condPagamento);
        startActivity(intent);
    }
}