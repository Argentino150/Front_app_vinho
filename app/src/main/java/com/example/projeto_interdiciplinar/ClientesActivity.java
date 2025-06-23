package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ClientePage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesActivity extends AppCompatActivity implements ClientesAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ClientesAdapter clientesAdapter;
    private ProgressBar progressBar;
    private List<Cliente> listaDeClientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        recyclerView = findViewById(R.id.recycler_view_clientes);
        progressBar = findViewById(R.id.progress_bar_clientes);

        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchClientes();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientesAdapter = new ClientesAdapter(listaDeClientes, this);
        recyclerView.setAdapter(clientesAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.lista_voltar).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, MenuRepresentanteActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.btn_adicionar_cliente).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, CadastroClienteActivity.class);
            startActivity(intent);
        });
    }

    private void fetchClientes() {
        //... (o método fetchClientes continua exatamente o mesmo)
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        ApiClient.getApiService().getClientes().enqueue(new Callback<ClientePage>() {
            @Override
            public void onResponse(@NonNull Call<ClientePage> call, @NonNull Response<ClientePage> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaDeClientes.clear();
                    listaDeClientes.addAll(response.body().getContent());
                    clientesAdapter.setData(listaDeClientes);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(ClientesActivity.this, "Falha ao carregar clientes.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ClientePage> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ClientesActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClick(Cliente cliente) {
        // --- AQUI A MUDANÇA ---
        // Abrir a tela de edição, passando o objeto Cliente
        Intent intent = new Intent(ClientesActivity.this, EditarClienteActivity.class);
        // "CLIENTE_PARA_EDITAR" é uma chave única para identificar o dado
        intent.putExtra("CLIENTE_PARA_EDITAR", cliente);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Cliente cliente) {
        //... (o método onDeleteClick continua exatamente o mesmo)
        showDeleteConfirmationDialog(cliente);
    }

    private void showDeleteConfirmationDialog(final Cliente clienteParaExcluir) {
        //... (seu método de exclusão continua o mesmo)
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aviso_excluir, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        Button btnContinuar = dialogView.findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(v -> {
            ApiClient.getApiService().deleteCliente(clienteParaExcluir.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ClientesActivity.this, "Cliente excluído!", Toast.LENGTH_SHORT).show();
                        fetchClientes();
                    } else {
                        Toast.makeText(ClientesActivity.this, "Erro ao excluir.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(ClientesActivity.this, "Falha de conexão.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}