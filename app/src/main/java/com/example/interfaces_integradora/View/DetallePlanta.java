package com.example.interfaces_integradora.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserValuesPlant;
import com.example.interfaces_integradora.Utility.NetworkChangeListener;
import com.example.interfaces_integradora.Utility.NotificationService;
import com.example.interfaces_integradora.ViewModel.ViewModelDetailPlant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class DetallePlanta extends AppCompatActivity {
    TextView Valorhumedad;
    LottieAnimationView animacion;
    LottieAnimationView animacion2;
    TextView ValorTemperatura;
    TextView ValorSuelo;
    TextView ValorMovimiento;
    TextView ValorLluvia;
    TextView ValorAgua;
    TextView ValorLuz;
    ImageView mov;
    String token;
    ViewModelDetailPlant viewModel;
    Handler handler;
    Runnable runnableCode;

    FloatingActionButton fab;

    SharedPreferences sharedPreferences;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
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
        mov = findViewById(R.id.mov);
        animacion = findViewById(R.id.animation_view);
        animacion2 = findViewById(R.id.animation_move);

        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("token", token);
        startService(intent);

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
                                mov.setVisibility(View.VISIBLE);
                                animacion2.setVisibility(View.GONE);
                            } else {
                                ValorMovimiento.setText("Con movimiento");
                                mov.setVisibility(View.GONE);
                                animacion2.setVisibility(View.VISIBLE);
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

        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                viewModel.loadPlantData(token);
                handler.postDelayed(this, 60000);
            }
        };
        handler.post(runnableCode);

        fab.setOnClickListener(view -> {
            viewModel.sendButton(token);
            viewModel.loadPlantData(token);
            Toast.makeText(this, "Enviando señal", Toast.LENGTH_SHORT).show();
            CountDownTimer s = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long l) {
                    animacion.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    animacion.setVisibility(View.GONE);
                    viewModel.getMessage().observe(DetallePlanta.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Toast.makeText(DetallePlanta.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            s.start();
        });

    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnableCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(runnableCode);
    }
}