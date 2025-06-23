package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Representante;
import com.example.projeto_interdiciplinar.dto.RepresentantePage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepresentantesActivity extends AppCompatActivity implements RepresentantesAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RepresentantesAdapter representantesAdapter;
    private ProgressBar progressBar;
    private List<Representante> listaDeRepresentantes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_representante);

        recyclerView = findViewById(R.id.recycler_view_representantes);
        progressBar = findViewById(R.id.progress_bar_representantes);

        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchRepresentantes();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        representantesAdapter = new RepresentantesAdapter(listaDeRepresentantes, this);
        recyclerView.setAdapter(representantesAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.lista_voltar).setOnClickListener(v -> {
            Intent intent = new Intent(RepresentantesActivity.this, MenuAdminActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.btn_adicionar_representante).setOnClickListener(v -> {
            Intent intent = new Intent(RepresentantesActivity.this, CadastroRepresentanteActivity.class);
            startActivity(intent);
        });
    }

    private void fetchRepresentantes() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiClient.getApiService().getRepresentantes().enqueue(new Callback<RepresentantePage>() {
            @Override
            public void onResponse(@NonNull Call<RepresentantePage> call, @NonNull Response<RepresentantePage> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaDeRepresentantes.clear();
                    listaDeRepresentantes.addAll(response.body().getContent());
                    representantesAdapter.setData(listaDeRepresentantes);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(RepresentantesActivity.this, "Falha ao carregar representantes.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<RepresentantePage> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RepresentantesActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClick(Representante representante) {
        Intent intent = new Intent(this, EditarRepresentanteActivity.class);
        intent.putExtra("REPRESENTANTE_PARA_EDITAR", representante);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Representante representante) {
        showDeleteConfirmationDialog(representante);
    }

    private void showDeleteConfirmationDialog(final Representante representanteParaExcluir) {
        // Seu método de diálogo, agora fazendo a chamada de API
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aviso_excluir, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        Button btnContinuar = dialogView.findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(v -> {
            ApiClient.getApiService().deleteRepresentante(representanteParaExcluir.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RepresentantesActivity.this, "Representante excluído!", Toast.LENGTH_SHORT).show();
                        fetchRepresentantes(); // Recarrega a lista para mostrar a remoção
                    } else {
                        Toast.makeText(RepresentantesActivity.this, "Erro ao excluir.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(RepresentantesActivity.this, "Falha de conexão.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}