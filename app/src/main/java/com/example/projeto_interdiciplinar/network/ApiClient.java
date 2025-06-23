package com.example.projeto_interdiciplinar.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // --- IMPORTANTE: URL BASE DO SEU BACKEND ---
    //
    // Para se conectar ao seu computador (localhost) a partir do Emulador do Android,
    // você DEVE usar o endereço IP especial 10.0.2.2.
    // O endereço "localhost" ou "127.0.0.1" dentro do emulador aponta para o próprio emulador, e não para o seu computador.
    //
    // Verifique se o seu backend Spring Boot está rodando na porta 8080. Se for outra porta, altere aqui.
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit = null;

    private static ApiService apiService = null;

    /**
     * Este método cria e configura um cliente HTTP (OkHttpClient) que registra
     * todas as informações das requisições e respostas no Logcat.
     * Isso é EXTREMAMENTE útil para depurar e ver se a comunicação com a API está correta.
     */
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    /**
     * Este método cria uma instância única do Retrofit (padrão Singleton).
     * Ele configura o Retrofit com a URL base, o cliente HTTP com logging,
     * e o conversor Gson para tratar os objetos JSON.
     */
    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(buildClient()) // Usa o cliente com logging
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Ponto de acesso público para o nosso ApiService.
     * É este método que vamos chamar das nossas Activities para fazer as requisições.
     * Ele garante que estamos sempre usando a mesma instância do serviço.
     */
    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(ApiService.class);
        }
        return apiService;
    }
}