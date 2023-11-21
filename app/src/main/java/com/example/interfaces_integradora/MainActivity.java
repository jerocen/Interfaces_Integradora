package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                Intent intent = new Intent(MainActivity.this, LogInView.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}