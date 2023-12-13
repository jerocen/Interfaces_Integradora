package com.example.interfaces_integradora.Repository;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserMe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilRepository {
    Peticiones peticiones = new Peticiones();

    public void obtenerDatosUser(String token, UserCallback callback) {
        Call<ResponseGetUserMe> call = peticiones.obtenerDatosUser(token);
        call.enqueue(new Callback<ResponseGetUserMe>() {
            @Override
            public void onResponse(Call<ResponseGetUserMe> call, Response<ResponseGetUserMe> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al obtener los datos del usuario"));
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserMe> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface UserCallback {
        void onSuccess(ResponseGetUserMe data);
        void onError(Throwable t);
    }
}
