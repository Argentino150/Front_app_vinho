package com.example.projeto_interdiciplinar.network;

import com.example.projeto_interdiciplinar.dto.*; // Usando wildcard para simplificar
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List; // NOVO IMPORT

public interface ApiService {

    // --- Autenticação ---
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // --- Vinhos ---
    @GET("vinhos")
    Call<VinhoPage> getVinhos();
    @POST("vinhos")
    Call<Vinho> createVinho(@Body Vinho vinho);
    @PUT("vinhos/{id}")
    Call<Vinho> updateVinho(@Path("id") Long id, @Body Vinho vinho);
    @DELETE("vinhos/{id}")
    Call<Void> deleteVinho(@Path("id") Long id);

    // --- Clientes ---
    @GET("clientes")
    Call<ClientePage> getClientes();
    @POST("clientes")
    Call<Cliente> createCliente(@Body ClienteCadastroDTO cliente);
    @PUT("clientes/{id}")
    Call<Cliente> updateCliente(@Path("id") Long id, @Body ClienteCadastroDTO cliente);
    @DELETE("clientes/{id}")
    Call<Void> deleteCliente(@Path("id") Long id);

    // --- Pedidos ---
    @GET("pedidos")
    Call<PedidoPage> getPedidos();
    @POST("pedidos")
    Call<Pedido> createPedido(@Body PedidoCadastroDTO pedido);
    @PUT("pedidos/{id}")
    Call<Pedido> updatePedido(@Path("id") Long id, @Body PedidoUpdateDTO pedido);

    // --- REPRESENTANTES (NOVOS ENDPOINTS) ---
    @GET("representantes")
    Call<RepresentantePage> getRepresentantes();

    @POST("representantes")
    Call<Representante> createRepresentante(@Body RepresentanteCadastroDTO representante);

    @PUT("representantes/{id}")
    Call<Representante> updateRepresentante(@Path("id") Long id, @Body Representante representante);

    @DELETE("representantes/{id}")
    Call<Void> deleteRepresentante(@Path("id") Long id);

    // --- RELATÓRIOS (NOVO ENDPOINT) ---
    @GET("relatorios/vendas/por-regiao")
    Call<List<VendasPorRegiaoDTO>> getVendasPorRegiao();

    // --- VISITAS (NOVOS ENDPOINTS) ---
    @GET("visitas")
    Call<VisitaPage> getVisitas();

    @POST("visitas")
    Call<Visita> createVisita(@Body VisitaCadastroDTO visita);

    @PUT("visitas/{id}")
    Call<Visita> updateVisita(@Path("id") Long id, @Body VisitaCadastroDTO visita);

    @DELETE("visitas/{id}")
    Call<Void> deleteVisita(@Path("id") Long id);
}