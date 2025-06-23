package com.example.projeto_interdiciplinar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Representante;
import java.util.List;

public class RepresentantesAdapter extends RecyclerView.Adapter<RepresentantesAdapter.RepresentanteViewHolder> {

    public interface OnItemClickListener {
        void onEditClick(Representante representante);
        void onDeleteClick(Representante representante);
    }

    private List<Representante> representantes;
    private final OnItemClickListener listener;

    public RepresentantesAdapter(List<Representante> representantes, OnItemClickListener listener) {
        this.representantes = representantes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RepresentanteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_representante, parent, false);
        return new RepresentanteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepresentanteViewHolder holder, int position) {
        Representante representante = representantes.get(position);
        holder.bind(representante, listener);
    }

    @Override
    public int getItemCount() {
        return representantes != null ? representantes.size() : 0;
    }

    public void setData(List<Representante> novosRepresentantes) {
        this.representantes = novosRepresentantes;
        notifyDataSetChanged();
    }

    static class RepresentanteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome, tvEmail;
        private ImageView ivEditar, ivExcluir;

        public RepresentanteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_representante);
            tvEmail = itemView.findViewById(R.id.tv_email_representante);
            ivEditar = itemView.findViewById(R.id.iv_editar_representante);
            ivExcluir = itemView.findViewById(R.id.iv_excluir_representante);
        }

        public void bind(final Representante representante, final OnItemClickListener listener) {
            tvNome.setText(String.format("%s %s", representante.getNome(), representante.getSobrenome()));
            tvEmail.setText(representante.getEmail());
            ivEditar.setOnClickListener(v -> listener.onEditClick(representante));
            ivExcluir.setOnClickListener(v -> listener.onDeleteClick(representante));
        }
    }
}