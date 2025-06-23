package com.example.projeto_interdiciplinar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.VendasPorRegiaoDTO;
import com.example.projeto_interdiciplinar.network.ApiClient;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatoriosActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        pieChart = findViewById(R.id.pie_chart_vendas);
        findViewById(R.id.btn_voltar_relatorio).setOnClickListener(v -> finish());

        fetchRelatorioData();
    }

    private void fetchRelatorioData() {
        ApiClient.getApiService().getVendasPorRegiao().enqueue(new Callback<List<VendasPorRegiaoDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<VendasPorRegiaoDTO>> call, @NonNull Response<List<VendasPorRegiaoDTO>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    setupPieChart(response.body());
                } else {
                    Toast.makeText(RelatoriosActivity.this, "Não há dados para exibir no relatório.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<VendasPorRegiaoDTO>> call, @NonNull Throwable t) {
                Toast.makeText(RelatoriosActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPieChart(List<VendasPorRegiaoDTO> dados) {
        // 1. Calcula o total de vendas para poder calcular a porcentagem
        float totalVendas = 0f;
        for (VendasPorRegiaoDTO item : dados) {
            if (item.getTotalVendas() != null) {
                totalVendas += item.getTotalVendas().floatValue();
            }
        }

        // 2. Converte os dados para o formato do gráfico
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (VendasPorRegiaoDTO item : dados) {
            entries.add(new PieEntry(item.getTotalVendas().floatValue(), item.getRegiao()));
        }

        // 3. Cria o conjunto de dados (DataSet)
        PieDataSet dataSet = new PieDataSet(entries, "");

        // **MUDANÇA 1: PALETA DE CORES CUSTOMIZADA COM TONS CLAROS**
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(179, 229, 252)); // Azul Claro
        colors.add(Color.rgb(255, 245, 157)); // Amarelo Claro
        colors.add(Color.rgb(144, 238, 144)); // Verde Claro
        colors.add(Color.rgb(255, 205, 210)); // Rosa Claro
        // Adiciona mais cores padrões caso tenha mais de 4 regiões
        for (int c : ColorTemplate.VORDIPLOM_COLORS) colors.add(c);
        dataSet.setColors(colors);
        // **FIM DA MUDANÇA 1**

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);
        dataSet.setSliceSpace(2f);

        // 4. Cria os dados finais e o formatador de valores
        PieData pieData = new PieData(dataSet);
        final float finalTotalVendas = totalVendas;
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                float percent = (value / finalTotalVendas) * 100f;
                String valorStr = "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%,.0f", value);
                String percentStr = String.format(Locale.getDefault(), "%.1f%%", percent);
                return valorStr + "\n(" + percentStr + ")";
            }
        });

        // 5. Configurações Finais do Gráfico
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(15, 15, 15, 15);

        // **MUDANÇA 2: REMOVER O BURACO DO MEIO**
        pieChart.setDrawHoleEnabled(false);
        // **FIM DA MUDANÇA 2**

        pieChart.setDrawEntryLabels(true); // Coloca o nome da região (Ex: "Sul") na fatia
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(16f);

        // Configurações da legenda (agora menos importante, mas ainda útil)
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false); // Vamos desabilitar a legenda para um visual mais limpo

        // Anima e atualiza
        pieChart.animateY(1200);
        pieChart.invalidate();
    }
}