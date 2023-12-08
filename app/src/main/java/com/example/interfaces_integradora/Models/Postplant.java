package com.example.interfaces_integradora.Models;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.PostUserPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Postplant {
    public static Call<ResponsePostUserPlant> createplant(String token, String nombrePlanta){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.0.199.187")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);

        // Crea una nueva instancia de PostUserPlant y establece el nombre de la planta
        PostUserPlant postUserPlant = new PostUserPlant();
        postUserPlant.setName(nombrePlanta);

        return apiRequest.createPlant("Bearer " + token, postUserPlant);
    }
}