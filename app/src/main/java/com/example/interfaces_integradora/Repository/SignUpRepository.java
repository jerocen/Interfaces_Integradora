package com.example.interfaces_integradora.Repository;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Models.PostUserRegister;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserRegister;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    private Peticiones peticiones = new Peticiones();

    public void registerUser(PostUserRegister user, RegisterCallback callback) {
        Call<ResponsePostUserRegister> call = peticiones.registerUser(user);
        call.enqueue(new Callback<ResponsePostUserRegister>() {
            @Override
            public void onResponse(Call<ResponsePostUserRegister> call, Response<ResponsePostUserRegister> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        ResponsePostUserRegister.ErrorResponse errorResp = new Gson().fromJson(response.errorBody().string(), ResponsePostUserRegister.ErrorResponse.class);
                        callback.onErrorResp(errorResp);
                    } catch (IOException e) {
                        callback.onError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserRegister> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface RegisterCallback {
        void onSuccess(ResponsePostUserRegister data);
        void onErrorResp(ResponsePostUserRegister.ErrorResponse errorResp);
        void onError(Throwable t);
    }
}
