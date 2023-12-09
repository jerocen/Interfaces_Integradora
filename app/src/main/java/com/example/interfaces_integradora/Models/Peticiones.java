package com.example.interfaces_integradora.Models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserChangePassword;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogout;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Peticiones {
    private ApiRequest apiRequest;

    public Peticiones() {
        this.apiRequest = RetrofitClient.getClient("http://52.0.199.187").create(ApiRequest.class);
    }

    public Call<ResponsePostUserPlant> createplant(String token, String nombrePlanta){
        // Crea una nueva instancia de PostUserPlant y establece el nombre de la planta
        PostUserPlant postUserPlant = new PostUserPlant(nombrePlanta);

        return apiRequest.createPlant("Bearer " + token, postUserPlant);
    }

    public Call<ResponsePostUserMe> obtenerDatosUser(String token) {
        return apiRequest.meUser("Bearer " + token);
    }

    public Call<ResponsePostUserChangePassword> changePassword(String token, String password, String password_confirmation) {
        PostUserChangePassword postUserChangePassword = new PostUserChangePassword(password, password_confirmation);

        return apiRequest.changePassword("Bearer " + token, postUserChangePassword);
    }

    public void logoutUser(Context context, String token, Runnable onSuccess) {
        Call<ResponsePostUserLogout> call = apiRequest.logoutUser("Bearer " + token);

        call.enqueue(new Callback<ResponsePostUserLogout>() {
            @Override
            public void onResponse(Call<ResponsePostUserLogout> call, Response<ResponsePostUserLogout> response) {
                if (response.isSuccessful()) {
                    Log.d("AuthLogout", "Logout successful");
                    // Borrar el token de SharedPreferences
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.remove("token");
                    myEdit.putBoolean("sesion", false);
                    myEdit.apply();

                    // Ejecutar el código de éxito
                    onSuccess.run();
                } else {
                    // Mostrar un mensaje de error
                    Log.e("AuthLogout", "Error response from server: " + response.code());
                    if (call != null && call.request() != null && call.request().body() != null) {
                        Log.e("AuthLogout", "Request body: " + call.request().body().toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponsePostUserLogout> call, Throwable t) {
                // Manejar el caso de error de red
                Toast.makeText(context, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
