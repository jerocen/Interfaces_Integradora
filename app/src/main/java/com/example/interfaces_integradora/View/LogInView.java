package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.interfaces_integradora.R;

public class LogInView extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_view);

        Button btnlogin = findViewById(R.id.btnlogin);
        TextView btnsignup = findViewById(R.id.registrate);

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LogInView.this, MyPlants.class);
                startActivity(intent);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LogInView.this, SignUpView.class);
                startActivity(intent);
            }
        });

    }

}