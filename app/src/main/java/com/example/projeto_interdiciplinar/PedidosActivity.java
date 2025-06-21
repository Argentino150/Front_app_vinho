package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PedidosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_pedidos);  // Aponte para o seu layout de listar pedidos


        // Botão para adicionar pedido
        Button btnAdicionarPedido = findViewById(R.id.btn_adicionar_pedido);
        btnAdicionarPedido.setOnClickListener(v -> {
            // Redireciona para a tela de cadastro de pedido
            Intent intent = new Intent(PedidosActivity.this, CadastroPedidoActivity.class);
            startActivity(intent);
        });

        // Exemplo de ícones de editar e excluir
        ImageView ivEditar = findViewById(R.id.iv_editar_1);
        ImageView ivExcluir = findViewById(R.id.iv_excluir_1);

        ivEditar.setOnClickListener(v -> {
            // Redireciona para a tela de edição de pedido
            Intent intent = new Intent(PedidosActivity.this, CadastroPedidoitemActivity.class);
            startActivity(intent);
        });

        ivExcluir.setOnClickListener(v -> {
            // Mostrar o dialog de aviso para confirmação de exclusão
            showDeleteConfirmationDialog();
        });

    }

    private void showDeleteConfirmationDialog() {
        // Inflar o layout do aviso
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aviso_excluir, null);

        // Inicializar o dialog com o layout customizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Inicializar os componentes dentro do dialog
        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        TextView txtMessage = dialogView.findViewById(R.id.txtMessage);
        Button btnContinuar = dialogView.findViewById(R.id.btnContinuar);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

        // Setar os textos no dialog
        txtTitle.setText(R.string.aviso);
        txtMessage.setText(R.string.mensagem_confirmacao);

        // Criar o dialog
        AlertDialog dialog = builder.create();

        // Botão de continuar - ação de exclusão
        btnContinuar.setOnClickListener(v -> {
            // Lógica de exclusão
            Toast.makeText(PedidosActivity.this, "Pedido excluído com sucesso!", Toast.LENGTH_SHORT).show();
            // Fechar o dialog após a confirmação
            dialog.dismiss();
        });

        // Botão de cancelar - fecha o dialog
        btnCancelar.setOnClickListener(v -> {
            // Fecha o dialog quando o botão "Cancelar" for clicado
            dialog.dismiss();
        });

        // Configuração do dialog
        builder.setCancelable(false); // Não permite fechar com o botão de voltar
        dialog.show();
    }
}
