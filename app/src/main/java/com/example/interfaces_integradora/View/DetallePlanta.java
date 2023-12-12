package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserBoton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetallePlanta extends AppCompatActivity {
    TextView Valorhumedad;
    LottieAnimationView animacion;
    TextView ValorTemperatura;
    TextView ValorSuelo;
    TextView ValorMovimiento;
    TextView ValorLluvia;
    TextView ValorAgua;
    TextView ValorLuz;
    String token;

    FloatingActionButton fab;

    SharedPreferences sharedPreferences;

    Peticiones Peticiones = new Peticiones();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_planta);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        Valorhumedad = findViewById(R.id.valor1);
        ValorTemperatura = findViewById(R.id.valor2);
        ValorSuelo = findViewById(R.id.valor3);
        ValorMovimiento = findViewById(R.id.valor4);
        ValorLluvia = findViewById(R.id.valor5);
        ValorAgua = findViewById(R.id.valor6);
        ValorLuz = findViewById(R.id.valor7);
        fab = findViewById(R.id.fab);
        animacion= findViewById(R.id.animation_view);

        Call<ResponseGetUserValuesPlant> call = Peticiones.obtenerDatosValuesPlant(token);
        call.enqueue(new Callback<ResponseGetUserValuesPlant>() {
            @Override
            public void onResponse(Call<ResponseGetUserValuesPlant> call, Response<ResponseGetUserValuesPlant> response) {
                if (response.isSuccessful()) {
                    ResponseGetUserValuesPlant responseBody = response.body();
                    List<ResponseGetUserValuesPlant.Data> dataList = responseBody.getData();

                    for (ResponseGetUserValuesPlant.Data data : dataList) {
                        switch (data.getFeedkey()) {
                            case "humedad":
                                Valorhumedad.setText(data.getValue());
                                break;
                            case "temperatura":
                                ValorTemperatura.setText(data.getValue());
                                break;
                            case "suelo":
                                ValorSuelo.setText(data.getValue());
                                break;
                            case "movimiento":
                                ValorMovimiento.setText(data.getValue());
                                break;
                            case "lluvia":
                                ValorLluvia.setText(data.getValue());
                                break;
                            case "agua":
                                ValorAgua.setText(data.getValue());
                                break;
                            case "luz":
                                ValorLuz.setText(data.getValue());
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserValuesPlant> call, Throwable t) {
                Toast.makeText(DetallePlanta.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(view -> {
            Call<ResponsePostUserBoton> call2 = Peticiones.sendBoton(token);
            call2.enqueue(new Callback<ResponsePostUserBoton>() {
                @Override
                public void onResponse(Call<ResponsePostUserBoton> call, Response<ResponsePostUserBoton> response) {
                    if (response.isSuccessful()) {
                        CountDownTimer s = new CountDownTimer(30000, 1000) {
                            @Override
                            public void onTick(long l) {
                                animacion.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                animacion.setVisibility(View.GONE);
                                Toast.makeText(DetallePlanta.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        };
                        s.start();


                    }
                }

                @Override
                public void onFailure(Call<ResponsePostUserBoton> call, Throwable t) {
                    Toast.makeText(DetallePlanta.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}