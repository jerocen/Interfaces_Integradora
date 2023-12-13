package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.interfaces_integradora.Utility.NotificationService;
import com.example.interfaces_integradora.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView animacion= findViewById(R.id.animation_view);
        Intent i = new Intent(this, LogInView.class);

        Intent serviceIntent = new Intent(this, NotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

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