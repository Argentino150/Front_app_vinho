package com.example.projeto_interdiciplinar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AgendaVisitaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_visitas);  // Aponte para o seu layout de listar visitas

        // Botão para adicionar visita
        Button btnAdicionarVisita = findViewById(R.id.btn_adicionar_visita);
        btnAdicionarVisita.setOnClickListener(v -> {
            // Redireciona para a tela de cadastro de visita
            Intent intent = new Intent(AgendaVisitaActivity.this, CadastroVisitaActivity.class);
            startActivity(intent);
        });

        // Exemplo de ícone de excluir (sem editar)
        ImageView ivExcluir = findViewById(R.id.iv_excluir_1);

        ivExcluir.setOnClickListener(v -> {
            // Mostrar o dialog de aviso para confirmação de exclusão
            showDeleteConfirmationDialog();
        });

        // Botão Voltar
        ImageView voltarButton = findViewById(R.id.lista_voltar);
        voltarButton.setOnClickListener(v -> {
            Intent intent = new Intent(AgendaVisitaActivity.this, MenuRepresentanteActivity.class);  // Corrigido para a atividade correta
            startActivity(intent);
            finish();
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
            Toast.makeText(AgendaVisitaActivity.this, "Vinho excluído com sucesso!", Toast.LENGTH_SHORT).show();
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
