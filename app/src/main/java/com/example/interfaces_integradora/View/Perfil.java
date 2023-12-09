package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;

import retrofit2.Call;

public class Perfil extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView nombre, correo;
    String token;
    Button btnEdit;

    Peticiones peticiones = new Peticiones();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);



        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        nombre = findViewById(R.id.nom);
        correo = findViewById(R.id.ema);
        btnEdit = findViewById(R.id.btn);

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(Perfil.this, EditProfileInfo.class);
            startActivity(i);
        });

        Call<ResponsePostUserMe> call = peticiones.obtenerDatosUser(token);
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
               Intent i = new Intent(Perfil.this, LogInView.class);
               startActivity(i);
               finish();
            }
        });
    }
}