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
import com.example.projeto_interdiciplinar.dto.Visita;
import com.example.projeto_interdiciplinar.dto.VisitaPage;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaVisitaActivity extends AppCompatActivity implements VisitasAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private VisitasAdapter visitasAdapter;
    private ProgressBar progressBar;
    private List<Visita> listaDeVisitas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_visitas);

        recyclerView = findViewById(R.id.recycler_view_visitas);
        progressBar = findViewById(R.id.progress_bar_visitas);

        setupRecyclerView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchVisitas();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        visitasAdapter = new VisitasAdapter(listaDeVisitas, this);
        recyclerView.setAdapter(visitasAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.lista_voltar).setOnClickListener(v -> finish());
        findViewById(R.id.btn_adicionar_visita).setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroVisitaActivity.class));
        });
    }

    private void fetchVisitas() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiClient.getApiService().getVisitas().enqueue(new Callback<VisitaPage>() {
            @Override
            public void onResponse(@NonNull Call<VisitaPage> call, @NonNull Response<VisitaPage> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaDeVisitas.clear();
                    listaDeVisitas.addAll(response.body().getContent());
                    visitasAdapter.setData(listaDeVisitas);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(AgendaVisitaActivity.this, "Falha ao carregar visitas.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<VisitaPage> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AgendaVisitaActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(Visita visita) {
        showDeleteConfirmationDialog(visita);
    }

    private void showDeleteConfirmationDialog(final Visita visitaParaExcluir) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aviso_excluir, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        Button btnContinuar = dialogView.findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(v -> {
            ApiClient.getApiService().deleteVisita(visitaParaExcluir.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AgendaVisitaActivity.this, "Visita excluída!", Toast.LENGTH_SHORT).show();
                        fetchVisitas();
                    } else {
                        Toast.makeText(AgendaVisitaActivity.this, "Erro ao excluir visita.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(AgendaVisitaActivity.this, "Falha de conexão.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}