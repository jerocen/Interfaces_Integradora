package com.example.interfaces_integradora.Repository;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserMe;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPlantRepository {
    private Peticiones peticiones = new Peticiones();

    public void obtenerDatosUser(String token, UserCallback callback) {
        Call<ResponseGetUserMe> call = peticiones.obtenerDatosUser(token);
        call.enqueue(new Callback<ResponseGetUserMe>() {
            @Override
            public void onResponse(Call<ResponseGetUserMe> call, Response<ResponseGetUserMe> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserMe> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void obtenerDatosPlant(String token, PlantCallback callback) {
        Call<ResponseGetUserPlant> call = peticiones.obtenerDatosPlant(token);
        call.enqueue(new Callback<ResponseGetUserPlant>() {
            @Override
            public void onResponse(Call<ResponseGetUserPlant> call, Response<ResponseGetUserPlant> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserPlant> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void agregarPlanta(String token, String nombrePlanta, AddPlantCallback callback) {
        Call<ResponsePostUserPlant> call = peticiones.createplant(token, nombrePlanta);
        call.enqueue(new Callback<ResponsePostUserPlant>() {
            @Override
            public void onResponse(Call<ResponsePostUserPlant> call, Response<ResponsePostUserPlant> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserPlant> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface UserCallback {
        void onSuccess(ResponseGetUserMe data);
        void onError(Throwable t);
    }

    public interface PlantCallback {
        void onSuccess(ResponseGetUserPlant data);
        void onError(Throwable t);
    }

    public interface AddPlantCallback {
        void onSuccess(ResponsePostUserPlant data);
        void onError(Throwable t);
    }
}

