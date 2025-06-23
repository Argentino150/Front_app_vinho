package com.example.projeto_interdiciplinar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Cliente;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder> {

    public interface OnItemClickListener {
        void onEditClick(Cliente cliente);
        void onDeleteClick(Cliente cliente);
    }

    private List<Cliente> clientes;
    private final OnItemClickListener listener;

    public ClientesAdapter(List<Cliente> clientes, OnItemClickListener listener) {
        this.clientes = clientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = clientes.get(position);
        holder.bind(cliente, listener);
    }

    @Override
    public int getItemCount() {
        return clientes != null ? clientes.size() : 0;
    }

    public void setData(List<Cliente> novosClientes) {
        this.clientes = novosClientes;
        notifyDataSetChanged();
    }

    static class ClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome, tvEmail;
        private ImageView ivEditar, ivExcluir;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_cliente);
            tvEmail = itemView.findViewById(R.id.tv_email_cliente);
            ivEditar = itemView.findViewById(R.id.iv_editar_cliente);
            ivExcluir = itemView.findViewById(R.id.iv_excluir_cliente);
        }

        public void bind(final Cliente cliente, final OnItemClickListener listener) {
            tvNome.setText(String.format("%s %s", cliente.getNome(), cliente.getSobrenome()));
            tvEmail.setText(cliente.getEmail());
            ivEditar.setOnClickListener(v -> listener.onEditClick(cliente));
            ivExcluir.setOnClickListener(v -> listener.onDeleteClick(cliente));
        }
    }
}