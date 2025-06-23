package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ItemPedidoDTO;
import com.example.projeto_interdiciplinar.dto.Pedido;
import com.example.projeto_interdiciplinar.dto.PedidoCadastroDTO;
import com.example.projeto_interdiciplinar.network.ApiClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resumopedido extends AppCompatActivity {

    private TextView tvDadosCliente, tvListaProdutos;
    private Button btnFinalizarPedido, btnVoltar;

    // Dados recebidos das telas anteriores
    private Long clienteId;
    private String condicoesPagamento;
    private ArrayList<ItemPedidoDTO> itensPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        initializeViews();
        getIntentData();

        // Se os dados foram recebidos corretamente, montamos o resumo
        if (clienteId != -1L) {
            populateResumo();
        }

        btnFinalizarPedido.setOnClickListener(v -> finalizarPedido());
        btnVoltar.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        tvDadosCliente = findViewById(R.id.tv_dados_cliente);
        tvListaProdutos = findViewById(R.id.tv_lista_produtos);
        btnFinalizarPedido = findViewById(R.id.btn_finalizar_pedido);
        btnVoltar = findViewById(R.id.btn_voltar_resumo); // Corrigido para o ID do seu XML de resumo
    }

    private void getIntentData() {
        clienteId = getIntent().getLongExtra("CLIENTE_ID", -1L);
        condicoesPagamento = getIntent().getStringExtra("CONDICOES_PAGAMENTO");
        itensPedido = (ArrayList<ItemPedidoDTO>) getIntent().getSerializableExtra("ITENS_PEDIDO");

        if (clienteId == -1L || condicoesPagamento == null || itensPedido == null) {
            Toast.makeText(this, "Erro ao receber dados do pedido.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void populateResumo() {
        // Para exibir o nome do cliente, precisamos buscá-lo na API usando o ID
        // (Isso será implementado no futuro, por enquanto vamos exibir o ID)
        String dadosClienteStr = String.format(
                Locale.getDefault(),
                "ID do Cliente: %d\nForma de pagamento: %s",
                clienteId,
                condicoesPagamento
        );
        tvDadosCliente.setText(dadosClienteStr);

        // Para exibir os detalhes dos produtos, precisaríamos dos nomes e preços.
        // Por enquanto, vamos exibir o ID do vinho e a quantidade.
        StringBuilder produtosStr = new StringBuilder();
        for (ItemPedidoDTO item : itensPedido) {
            produtosStr.append(String.format(
                    Locale.getDefault(),
                    "Vinho ID: %d | Quantidade: %d\n",
                    item.getVinhoId(),
                    item.getQuantidade()
            ));
        }
        tvListaProdutos.setText(produtosStr.toString());
    }

    private void finalizarPedido() {
        // TODO: Para um app real, o ID do representante logado deveria vir do SharedPreferences
        Long representanteId = 1L; // Usando um valor fixo para teste

        // 1. Monta o DTO final para enviar à API
        PedidoCadastroDTO pedidoFinal = new PedidoCadastroDTO(
                clienteId,
                representanteId,
                condicoesPagamento,
                itensPedido
        );

        // 2. Faz a chamada para a API
        ApiClient.getApiService().createPedido(pedidoFinal).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(@NonNull Call<Pedido> call, @NonNull Response<Pedido> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Resumopedido.this, "Pedido criado com sucesso!", Toast.LENGTH_LONG).show();

                    // Limpa as telas de criação e volta para a lista de pedidos
                    Intent intent = new Intent(Resumopedido.this, PedidosActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Resumopedido.this, "Erro ao criar pedido. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pedido> call, @NonNull Throwable t) {
                Toast.makeText(Resumopedido.this, "Falha de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}