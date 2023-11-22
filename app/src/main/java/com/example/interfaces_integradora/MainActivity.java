package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView animacion= findViewById(R.id.animation_view);
        Intent i = new Intent(this, LogInView.class);

        CountDownTimer s = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                animacion.setVisibility(View.GONE);
                startActivity(i);
                finish();
            }
        };
        s.start();
    }
}