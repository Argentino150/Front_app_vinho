package com.example.projeto_interdiciplinar.network;

import com.example.projeto_interdiciplinar.dto.LoginRequest;
import com.example.projeto_interdiciplinar.dto.LoginResponse;
import com.example.projeto_interdiciplinar.dto.Vinho; // NOVO IMPORT
import com.example.projeto_interdiciplinar.dto.VinhoPage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT; // NOVO IMPORT
import retrofit2.http.Path;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("vinhos")
    Call<VinhoPage> getVinhos();

    @DELETE("vinhos/{id}")
    Call<Void> deleteVinho(@Path("id") Long id);

    /**
     * NOVO ENDPOINT: Atualiza um vinho existente.
     * @PUT("vinhos/{id}") -> Indica o método PUT.
     * @Path("id") -> ID do vinho a ser atualizado.
     * @Body Vinho -> O corpo da requisição com os novos dados do vinho.
     * O backend retorna o vinho atualizado, por isso Call<Vinho>.
     */
    @PUT("vinhos/{id}")
    Call<Vinho> updateVinho(@Path("id") Long id, @Body Vinho vinho);
}