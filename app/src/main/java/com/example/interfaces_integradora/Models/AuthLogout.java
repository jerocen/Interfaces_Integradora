package com.example.interfaces_integradora.Models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogout;
import com.example.interfaces_integradora.View.LogInView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthLogout {
    public static void logoutUser(Context context, String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.0.199.187")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
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

                    // Navegar a la siguiente actividad
                    Intent intent = new Intent(context, LogInView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);

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
