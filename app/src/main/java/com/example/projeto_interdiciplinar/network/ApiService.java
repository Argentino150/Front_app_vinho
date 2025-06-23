package com.example.projeto_interdiciplinar.network;

import com.example.projeto_interdiciplinar.dto.Cliente;
import com.example.projeto_interdiciplinar.dto.ClienteCadastroDTO;
import com.example.projeto_interdiciplinar.dto.ClientePage;
import com.example.projeto_interdiciplinar.dto.LoginRequest;
import com.example.projeto_interdiciplinar.dto.LoginResponse;
import com.example.projeto_interdiciplinar.dto.Vinho;
import com.example.projeto_interdiciplinar.dto.VinhoPage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // --- Autenticação ---
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // --- Vinhos ---
    @GET("vinhos")
    Call<VinhoPage> getVinhos();

    @POST("vinhos") // MÉTODO ADICIONADO
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
}