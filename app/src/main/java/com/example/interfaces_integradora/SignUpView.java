package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUpView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);

        TextView textView = findViewById(R.id.textpregunYaTieCue);

        TextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(SignUpView.this, LogInView.class);
                startActivity(intent);
            }
        });

        TextView textView2 = findViewById(R.id.textpreguninicsesio);

        TextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignUpView.this, LogInView.class);
                startActivity(intent);
            }
        });
    }
}