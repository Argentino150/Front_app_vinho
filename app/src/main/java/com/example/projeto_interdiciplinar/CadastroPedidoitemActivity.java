package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.ItemPedidoDTO;
import com.example.projeto_interdiciplinar.dto.Vinho;
import com.example.projeto_interdiciplinar.dto.VinhoPage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroPedidoitemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VinhosParaPedidoAdapter adapter;
    private Long clienteId;
    private String condicoesPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido_itens);

        // Recebe os dados da tela anterior
        clienteId = getIntent().getLongExtra("CLIENTE_ID", -1L);
        condicoesPagamento = getIntent().getStringExtra("CONDICOES_PAGAMENTO");

        if (clienteId == -1L) {
            Toast.makeText(this, "Erro: ID do cliente não encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupRecyclerView();
        fetchVinhosDisponiveis();

        findViewById(R.id.btn_ver_resumo).setOnClickListener(v -> irParaResumo());
        findViewById(R.id.btn_voltar).setOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_vinhos_pedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VinhosParaPedidoAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void fetchVinhosDisponiveis() {
        ApiClient.getApiService().getVinhos().enqueue(new Callback<VinhoPage>() {
            @Override
            public void onResponse(@NonNull Call<VinhoPage> call, @NonNull Response<VinhoPage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body().getContent());
                } else {
                    Toast.makeText(CadastroPedidoitemActivity.this, "Falha ao buscar vinhos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<VinhoPage> call, @NonNull Throwable t) {
                Toast.makeText(CadastroPedidoitemActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irParaResumo() {
        ArrayList<ItemPedidoDTO> itensSelecionados = adapter.getItensSelecionados();

        if (itensSelecionados.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um item ao pedido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Resumopedido.class);
        intent.putExtra("CLIENTE_ID", clienteId);
        intent.putExtra("CONDICOES_PAGAMENTO", condicoesPagamento);
        intent.putExtra("ITENS_PEDIDO", itensSelecionados); // Passa a lista de itens
        startActivity(intent);
    }
}