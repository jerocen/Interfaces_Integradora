package com.example.interfaces_integradora.Models;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogout;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatosUser {
    public static Call<ResponsePostUserMe> obtenerDatosUser(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.0.199.187")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        return apiRequest.meUser("Bearer " + token);

        /*Call<ResponsePostUserMe> call = DatosUser.obtenerDatosUser(token);
        call.enqueue(new Callback<ResponsePostUserMe>() {
            @Override
            public void onResponse(Call<ResponsePostUserMe> call, Response<ResponsePostUserMe> response) {
                if (response.isSuccessful()) {
                    String username = response.body().getName();
                    textViewUsername.setText(username);
                } else {
                    // Manejar error
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserMe> call, Throwable t) {
                // Manejar error
            }
        });
        */
         
    }
}
