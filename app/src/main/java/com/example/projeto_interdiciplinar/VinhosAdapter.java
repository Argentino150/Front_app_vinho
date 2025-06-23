package com.example.projeto_interdiciplinar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import da biblioteca de imagem
import com.example.projeto_interdiciplinar.dto.Vinho;

import java.util.List;
import java.util.Locale;

public class VinhosAdapter extends RecyclerView.Adapter<VinhosAdapter.VinhoViewHolder> {

    public interface OnItemClickListener {
        void onEditClick(Vinho vinho);
        void onDeleteClick(Vinho vinho);
    }

    private List<Vinho> vinhos;
    private final OnItemClickListener listener;

    public VinhosAdapter(List<Vinho> vinhos, OnItemClickListener listener) {
        this.vinhos = vinhos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vinho, parent, false);
        return new VinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VinhoViewHolder holder, int position) {
        Vinho vinho = vinhos.get(position);
        holder.bind(vinho, listener);
    }

    @Override
    public int getItemCount() {
        return vinhos != null ? vinhos.size() : 0;
    }

    public void setData(List<Vinho> novosVinhos) {
        this.vinhos = novosVinhos;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        vinhos.remove(position);
        notifyItemRemoved(position);
    }

    static class VinhoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome, tvTipo, tvSafra, tvPreco;
        private ImageView ivImagem, ivEditar, ivExcluir;

        public VinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_vinho);
            tvTipo = itemView.findViewById(R.id.tv_tipo_vinho);
            tvSafra = itemView.findViewById(R.id.tv_safra_vinho);
            tvPreco = itemView.findViewById(R.id.tv_preco_vinho);
            ivImagem = itemView.findViewById(R.id.iv_imagem_vinho);
            ivEditar = itemView.findViewById(R.id.iv_editar);
            ivExcluir = itemView.findViewById(R.id.iv_excluir);
        }

        public void bind(final Vinho vinho, final OnItemClickListener listener) {
            tvNome.setText(vinho.getNome());
            tvTipo.setText(vinho.getTipo());
            tvSafra.setText(String.format(Locale.getDefault(), "Safra: %d", vinho.getSafra()));

            if (vinho.getPrecoUnitario() != null) {
                tvPreco.setText(String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", vinho.getPrecoUnitario()));
            } else {
                tvPreco.setText("R$ --");
            }

            // Lógica para carregar a imagem da URL
            if (vinho.getImagemUrl() != null && !vinho.getImagemUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(vinho.getImagemUrl()) // Pega a URL do vinho
                        .placeholder(R.drawable.logo1) // Imagem temporária que aparece enquanto carrega
                        .error(R.drawable.logo2) // Imagem que aparece se der erro ao carregar
                        .into(ivImagem); // O ImageView onde a imagem vai aparecer
            } else {
                // Se não houver URL salva, mostra a sua imagem padrão
                ivImagem.setImageResource(R.drawable.imagem);
            }

            ivEditar.setOnClickListener(v -> listener.onEditClick(vinho));
            ivExcluir.setOnClickListener(v -> listener.onDeleteClick(vinho));
        }
    }
}