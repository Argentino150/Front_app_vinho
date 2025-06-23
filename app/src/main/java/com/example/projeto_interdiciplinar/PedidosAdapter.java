package com.example.projeto_interdiciplinar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.Pedido;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;

    public PedidosAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.bind(pedido);
    }

    @Override
    public int getItemCount() {
        return pedidos != null ? pedidos.size() : 0;
    }

    public void setData(List<Pedido> novosPedidos) {
        this.pedidos = novosPedidos;
        notifyDataSetChanged();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvNomeCliente, tvData, tvValorTotal, tvStatus;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id_pedido);
            tvNomeCliente = itemView.findViewById(R.id.tv_nome_cliente_pedido);
            tvData = itemView.findViewById(R.id.tv_data_pedido);
            tvValorTotal = itemView.findViewById(R.id.tv_valor_total_pedido);
            tvStatus = itemView.findViewById(R.id.tv_status_pedido);
        }

        public void bind(final Pedido pedido) {
            tvId.setText(String.format("Pedido #%d", pedido.getId()));
            if (pedido.getCliente() != null) {
                tvNomeCliente.setText(String.format("Cliente: %s", pedido.getCliente().getNome()));
            }
            if (pedido.getDataDoPedido() != null && !pedido.getDataDoPedido().isEmpty()) {
                try {
                    LocalDateTime data = LocalDateTime.parse(pedido.getDataDoPedido());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    tvData.setText(String.format("Data: %s", data.format(formatter)));
                } catch (Exception e) {
                    tvData.setText("Data: inválida");
                }
            }
            if (pedido.getValorTotal() != null) {
                tvValorTotal.setText(String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", pedido.getValorTotal()));
            }
            tvStatus.setText(pedido.getStatus());
        }
    }
}