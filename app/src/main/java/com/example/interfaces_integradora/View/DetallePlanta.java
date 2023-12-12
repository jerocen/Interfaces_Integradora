package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.interfaces_integradora.ViewModel.ViewModelDetailPlant;
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

    ViewModelDetailPlant viewModel;

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

        viewModel = new ViewModelProvider(this).get(ViewModelDetailPlant.class);
        viewModel.getPlantData().observe(this, new Observer<List<ResponseGetUserValuesPlant.Data>>() {
            @Override
            public void onChanged(List<ResponseGetUserValuesPlant.Data> data) {
                for (ResponseGetUserValuesPlant.Data dataItem : data) {
                    String value = dataItem.getValue();
                    switch (dataItem.getFeedkey()) {
                        case "humedad":
                            Valorhumedad.setText(value + "%");
                            break;
                        case "temperatura":
                            ValorTemperatura.setText(value + "°F");
                            break;
                        case "suelo":
                            ValorSuelo.setText(value + "% ");
                            break;
                        case "movimiento":
                            if ("1".equals(value)) {
                                ValorMovimiento.setText("Sin movimiento");
                            } else {
                                ValorMovimiento.setText("Con movimiento");
                            }
                            break;
                        case "lluvia":
                            if ("1".equals(value)) {
                                ValorLluvia.setText("Sin lluvia");
                            } else {
                                ValorLluvia.setText("Lloviendo");
                            }
                            break;
                        case "agua":
                            ValorAgua.setText(value + "%");
                            break;
                        case "luz":
                            ValorLuz.setText(value + "%");
                            break;
                    }
                }
            }
        });
        viewModel.loadPlantData(token);

        fab.setOnClickListener(view -> {
            viewModel.sendButton(token);
            CountDownTimer s = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long l) {
                    animacion.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    animacion.setVisibility(View.GONE);
                    Toast.makeText(DetallePlanta.this, "Botón enviado", Toast.LENGTH_SHORT).show();
                }
            };
            s.start();
        });
    }
}