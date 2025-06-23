package com.example.projeto_interdiciplinar;

import android.text.Editable; // NOVO IMPORT
import android.text.TextWatcher; // NOVO IMPORT
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeto_interdiciplinar.dto.ItemPedidoDTO;
import com.example.projeto_interdiciplinar.dto.Vinho;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VinhosParaPedidoAdapter extends RecyclerView.Adapter<VinhosParaPedidoAdapter.VinhoParaPedidoViewHolder> {

    private List<Vinho> vinhosDisponiveis;
    private int[] quantidades;

    public VinhosParaPedidoAdapter(List<Vinho> vinhos) {
        this.vinhosDisponiveis = vinhos;
        this.quantidades = new int[vinhos.size()];
    }

    @NonNull @Override
    public VinhoParaPedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vinho_para_pedido, parent, false);
        return new VinhoParaPedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VinhoParaPedidoViewHolder holder, int position) {
        Vinho vinho = vinhosDisponiveis.get(position);
        holder.bind(vinho);
    }

    @Override public int getItemCount() {
        return vinhosDisponiveis.size();
    }

    public ArrayList<ItemPedidoDTO> getItensSelecionados() {
        ArrayList<ItemPedidoDTO> itens = new ArrayList<>();
        for (int i = 0; i < vinhosDisponiveis.size(); i++) {
            if (quantidades[i] > 0) {
                Vinho vinho = vinhosDisponiveis.get(i);
                itens.add(new ItemPedidoDTO(vinho.getId(), quantidades[i]));
            }
        }
        return itens;
    }

    public void setData(List<Vinho> novosVinhos) {
        this.vinhosDisponiveis = novosVinhos;
        this.quantidades = new int[novosVinhos.size()];
        notifyDataSetChanged();
    }

    // ViewHolder interno
    class VinhoParaPedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvPreco;
        EditText etQuantidade;

        public VinhoParaPedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_vinho);
            tvPreco = itemView.findViewById(R.id.tv_preco_vinho);
            etQuantidade = itemView.findViewById(R.id.et_quantidade);

            // --- AQUI ESTÁ A CORREÇÃO ---
            // Usamos um TextWatcher para capturar a mudança em tempo real.
            etQuantidade.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Não precisamos fazer nada aqui
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Também não precisamos fazer nada aqui
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Após o texto mudar, salvamos o valor
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        try {
                            quantidades[getAdapterPosition()] = Integer.parseInt(s.toString());
                        } catch (NumberFormatException e) {
                            quantidades[getAdapterPosition()] = 0;
                        }
                    }
                }
            });
        }

        public void bind(Vinho vinho) {
            tvNome.setText(vinho.getNome());
            if (vinho.getPrecoUnitario() != null) {
                tvPreco.setText(String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", vinho.getPrecoUnitario()));
            } else {
                tvPreco.setText("R$ --");
            }
            // Garante que o valor no EditText corresponda ao valor no array
            etQuantidade.setText(String.valueOf(quantidades[getAdapterPosition()]));
        }
    }
}