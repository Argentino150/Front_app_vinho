package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Pedido;
import com.example.projeto_interdiciplinar.dto.PedidoPage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PedidosAdapter pedidosAdapter;
    private ProgressBar progressBar;
    private List<Pedido> listaDePedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_pedidos);

        recyclerView = findViewById(R.id.recycler_view_pedidos);
        progressBar = findViewById(R.id.progress_bar_pedidos);

        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPedidos();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pedidosAdapter = new PedidosAdapter(listaDePedidos);
        recyclerView.setAdapter(pedidosAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.lista_voltar).setOnClickListener(v -> {
            Intent intent = new Intent(PedidosActivity.this, MenuRepresentanteActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.btn_adicionar_pedido).setOnClickListener(v -> {
            Intent intent = new Intent(PedidosActivity.this, CadastroPedidoActivity.class);
            startActivity(intent);
        });
    }

    private void fetchPedidos() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiClient.getApiService().getPedidos().enqueue(new Callback<PedidoPage>() {
            @Override
            public void onResponse(@NonNull Call<PedidoPage> call, @NonNull Response<PedidoPage> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaDePedidos.clear();
                    listaDePedidos.addAll(response.body().getContent());
                    pedidosAdapter.setData(listaDePedidos);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(PedidosActivity.this, "Falha ao carregar pedidos.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<PedidoPage> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PedidosActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}