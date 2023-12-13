package com.example.interfaces_integradora.Repository;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.Models.PostUserLogin;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserForgetPassword;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    Peticiones peticiones = new Peticiones();

    public void loginUser(PostUserLogin postUserLogin, LoginCallback callback) {
        Call<ResponsePostUserLogin> call = peticiones.loginUser(postUserLogin);
        call.enqueue(new Callback<ResponsePostUserLogin>() {
            @Override
            public void onResponse(Call<ResponsePostUserLogin> call, Response<ResponsePostUserLogin> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    if (response.code() == 401) {
                        callback.onError(new Exception("Credenciales incorrectas"));
                    } else if (response.code() == 403) {
                        callback.onError(new Exception("Cuenta no activa"));
                    } else {
                        callback.onError(new Exception("Error desconocido"));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserLogin> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void forgetPassword(String email, ForgetPasswordCallback callback) {
        Call<ResponsePostUserForgetPassword> call = peticiones.forgetPassword(email);
        call.enqueue(new Callback<ResponsePostUserForgetPassword>() {
            @Override
            public void onResponse(Call<ResponsePostUserForgetPassword> call, Response<ResponsePostUserForgetPassword> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en la respuesta del servidor"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserForgetPassword> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(ResponsePostUserLogin data);
        void onError(Throwable t);
    }

    public interface ForgetPasswordCallback {
        void onSuccess(ResponsePostUserForgetPassword data);
        void onError(Throwable t);
    }
}
