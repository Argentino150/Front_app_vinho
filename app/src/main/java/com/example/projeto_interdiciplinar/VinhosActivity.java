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
import com.example.projeto_interdiciplinar.dto.Vinho;
import com.example.projeto_interdiciplinar.dto.VinhoPage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VinhosActivity extends AppCompatActivity implements VinhosAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private VinhosAdapter vinhosAdapter;
    private ProgressBar progressBar;
    private List<Vinho> listaDeVinhos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vinhos);

        recyclerView = findViewById(R.id.recycler_view_vinhos);
        progressBar = findViewById(R.id.progress_bar);

        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Busca os vinhos toda vez que a tela se torna visível.
        // Isso garante que a lista esteja sempre atualizada ao voltar da tela de edição/criação.
        fetchVinhos();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vinhosAdapter = new VinhosAdapter(listaDeVinhos, this);
        recyclerView.setAdapter(vinhosAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.btn_adicionar_vinho).setOnClickListener(v -> {
            Intent intent = new Intent(VinhosActivity.this, CadastroVinhoActivity.class);
            startActivity(intent);
        });
        // AQUI ESTÁ A CORREÇÃO:
        findViewById(R.id.lista_voltar).setOnClickListener(v -> {
            finish(); // Simplesmente fecha a tela atual e volta para a anterior
        });
    }

    private void fetchVinhos() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiClient.getApiService().getVinhos().enqueue(new Callback<VinhoPage>() {
            @Override
            public void onResponse(@NonNull Call<VinhoPage> call, @NonNull Response<VinhoPage> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaDeVinhos.clear();
                    listaDeVinhos.addAll(response.body().getContent());
                    vinhosAdapter.setData(listaDeVinhos);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(VinhosActivity.this, "Falha ao carregar os vinhos.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<VinhoPage> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VinhosActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClick(Vinho vinho) {
        // AQUI A MUDANÇA: Abrir a tela de edição, passando o objeto Vinho
        Intent intent = new Intent(VinhosActivity.this, EditarVinhoActivity.class);
        intent.putExtra("VINHO_PARA_EDITAR", vinho); // "VINHO_PARA_EDITAR" é uma chave única
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Vinho vinho) {
        showDeleteConfirmationDialog(vinho);
    }

    private void showDeleteConfirmationDialog(final Vinho vinhoParaExcluir) {
        // ... (seu método de exclusão continua o mesmo)
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aviso_excluir, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        Button btnContinuar = dialogView.findViewById(R.id.btnContinuar);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        final AlertDialog dialog = builder.create();
        btnContinuar.setOnClickListener(v -> {
            ApiClient.getApiService().deleteVinho(vinhoParaExcluir.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(VinhosActivity.this, "Vinho excluído com sucesso!", Toast.LENGTH_SHORT).show();
                        int position = listaDeVinhos.indexOf(vinhoParaExcluir);
                        if (position != -1) {
                            listaDeVinhos.remove(position); // Remove da lista local
                            vinhosAdapter.notifyItemRemoved(position); // Notifica o adapter
                        }
                    } else {
                        Toast.makeText(VinhosActivity.this, "Erro ao excluir o vinho.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(VinhosActivity.this, "Falha na conexão ao excluir.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}