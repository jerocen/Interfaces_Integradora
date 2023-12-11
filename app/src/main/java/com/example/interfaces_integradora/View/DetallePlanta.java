package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetallePlanta extends AppCompatActivity {
    TextView Valorhumedad;
    TextView ValorTemperatura;
    TextView ValorSuelo;
    TextView ValorMovimiento;
    TextView ValorLluvia;
    TextView ValorAgua;
    TextView ValorLuz;

    String token;

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
    }
}