package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.interfaces_integradora.Models.DatosUser;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;

import retrofit2.Call;

public class Perfil extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView nombre, correo;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        nombre = findViewById(R.id.nom);
        correo = findViewById(R.id.ema);

        Call<ResponsePostUserMe> call = DatosUser.obtenerDatosUser(token);
        call.enqueue(new retrofit2.Callback<ResponsePostUserMe>() {
            @Override
            public void onResponse(Call<ResponsePostUserMe> call, retrofit2.Response<ResponsePostUserMe> response) {
                if (response.isSuccessful()) {
                    nombre.setText(response.body().getName());
                    correo.setText(response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserMe> call, Throwable t) {

            }
        });
    }
}