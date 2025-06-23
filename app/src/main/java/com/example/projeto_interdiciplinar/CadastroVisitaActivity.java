package com.example.projeto_interdiciplinar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ClientePage;
import com.example.projeto_interdiciplinar.dto.Visita;
import com.example.projeto_interdiciplinar.dto.VisitaCadastroDTO;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroVisitaActivity extends AppCompatActivity {

    private Spinner spinnerClientes;
    private TextView tvDataHoraSelecionada;
    private EditText etObservacoes;
    private Button btnSalvar, btnCancelar;

    private List<Cliente> listaDeClientes = new ArrayList<>();
    private LocalDateTime dataHoraSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_visita);

        initializeViews();
        fetchClientesParaSpinner();

        tvDataHoraSelecionada.setOnClickListener(v -> showDateTimePicker());
        btnSalvar.setOnClickListener(v -> createVisita());
        btnCancelar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        spinnerClientes = findViewById(R.id.spinner_clientes_visita);
        tvDataHoraSelecionada = findViewById(R.id.tv_data_hora_selecionada);
        etObservacoes = findViewById(R.id.et_observacoes);
        btnSalvar = findViewById(R.id.btn_save);
        btnCancelar = findViewById(R.id.btn_cancel);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CadastroVisitaActivity.this, android.R.layout.simple_spinner_item, nomesClientes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);
                } else {
                    Toast.makeText(CadastroVisitaActivity.this, "Falha ao carregar clientes.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ClientePage> call, @NonNull Throwable t) {
                Toast.makeText(CadastroVisitaActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                dataHoraSelecionada = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                tvDataHoraSelecionada.setText(dataHoraSelecionada.format(formatter));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void createVisita() {
        if (listaDeClientes.isEmpty()) {
            Toast.makeText(this, "Nenhum cliente disponível para agendar visita.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataHoraSelecionada == null) {
            Toast.makeText(this, "Por favor, selecione data e hora.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente clienteSelecionado = listaDeClientes.get(spinnerClientes.getSelectedItemPosition());
        Long clienteId = clienteSelecionado.getId();
        String observacoes = etObservacoes.getText().toString().trim();

        // Formata a data para o padrão ISO que o backend espera (Ex: "2025-07-20T11:00:00")
        String dataFormatada = dataHoraSelecionada.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // TODO: Pegar o ID do representante logado via SharedPreferences
        Long representanteId = 1L;

        VisitaCadastroDTO novaVisita = new VisitaCadastroDTO(clienteId, representanteId, dataFormatada, observacoes, "AGENDADA");

        ApiClient.getApiService().createVisita(novaVisita).enqueue(new Callback<Visita>() {
            @Override
            public void onResponse(@NonNull Call<Visita> call, @NonNull Response<Visita> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroVisitaActivity.this, "Visita agendada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CadastroVisitaActivity.this, "Erro ao agendar visita.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Visita> call, @NonNull Throwable t) {
                Toast.makeText(CadastroVisitaActivity.this, "Falha de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}