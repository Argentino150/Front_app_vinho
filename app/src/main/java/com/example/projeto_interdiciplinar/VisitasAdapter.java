package com.example.projeto_interdiciplinar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Visita;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class VisitasAdapter extends RecyclerView.Adapter<VisitasAdapter.VisitaViewHolder> {

    public interface OnItemClickListener {
        void onDeleteClick(Visita visita);
    }

    private List<Visita> visitas;
    private final OnItemClickListener listener;

    public VisitasAdapter(List<Visita> visitas, OnItemClickListener listener) {
        this.visitas = visitas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VisitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita, parent, false);
        return new VisitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitaViewHolder holder, int position) {
        Visita visita = visitas.get(position);
        holder.bind(visita, listener);
    }

    @Override
    public int getItemCount() {
        return visitas != null ? visitas.size() : 0;
    }

    public void setData(List<Visita> novasVisitas) {
        this.visitas = novasVisitas;
        notifyDataSetChanged();
    }

    static class VisitaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNomeCliente, tvDataHora, tvStatus;
        private ImageView ivExcluir;

        public VisitaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeCliente = itemView.findViewById(R.id.tv_nome_cliente_visita);
            tvDataHora = itemView.findViewById(R.id.tv_data_hora_visita);
            tvStatus = itemView.findViewById(R.id.tv_status_visita);
            ivExcluir = itemView.findViewById(R.id.iv_excluir_visita);
        }

        public void bind(final Visita visita, final OnItemClickListener listener) {
            if (visita.getCliente() != null) {
                tvNomeCliente.setText(String.format("Cliente: %s", visita.getCliente().getNome()));
            } else {
                tvNomeCliente.setText("Cliente não informado");
            }

            if (visita.getDataHora() != null && !visita.getDataHora().isEmpty()) {
                try {
                    LocalDateTime data = LocalDateTime.parse(visita.getDataHora());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm", new Locale("pt", "BR"));
                    tvDataHora.setText(String.format("Data: %s", data.format(formatter)));
                } catch (Exception e) {
                    tvDataHora.setText("Data: inválida");
                }
            }

            tvStatus.setText(String.format("Status: %s", visita.getStatus()));
            ivExcluir.setOnClickListener(v -> listener.onDeleteClick(visita));
        }
    }
}