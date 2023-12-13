package com.example.interfaces_integradora.Repository;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserBoton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantRepository {
    Peticiones peticiones = new Peticiones();

    public void getPlantData(String token, DataCallback callback) {
        Call<ResponseGetUserValuesPlant> call = peticiones.obtenerDatosValuesPlant(token);
        call.enqueue(new Callback<ResponseGetUserValuesPlant>() {
            @Override
            public void onResponse(Call<ResponseGetUserValuesPlant> call, Response<ResponseGetUserValuesPlant> response) {
                if (response.isSuccessful()) {
                    ResponseGetUserValuesPlant responseBody = response.body();
                    List<ResponseGetUserValuesPlant.Data> dataList = responseBody.getData();
                    callback.onSuccess(dataList);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserValuesPlant> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void sendButton(String token, ButtonCallback callback) {
        Call<ResponsePostUserBoton> call = peticiones.sendBoton(token);
        call.enqueue(new Callback<ResponsePostUserBoton>() {
            @Override
            public void onResponse(Call<ResponsePostUserBoton> call, Response<ResponsePostUserBoton> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserBoton> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface DataCallback {
        void onSuccess(List<ResponseGetUserValuesPlant.Data> data);
        void onError(Throwable t);
    }

    public interface ButtonCallback {
        void onSuccess(String message);
        void onError(Throwable t);
    }
}
