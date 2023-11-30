package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ApiRequest;
import com.example.interfaces_integradora.Retrofit.PostUserRegister;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);

        EditText nombre = findViewById(R.id.editTextNombreRegistro);
        EditText email = findViewById(R.id.editTextEmailRegistro);
        EditText password = findViewById(R.id.editTextContraseniaRegistro);
        EditText passwordConfirm = findViewById(R.id.editTextConfirmarContraseniaRegistro);
        Button btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPasswordConfirm = passwordConfirm.getText().toString();

                if (userPassword.equals(userPasswordConfirm)) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://52.0.199.187")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiRequest apiRequest = retrofit.create(ApiRequest.class);
                    PostUserRegister user = new PostUserRegister(name, userEmail, userPassword);
                    Call<ResponsePostUserRegister> call = apiRequest.registerUser(user);

                    call.enqueue(new Callback<ResponsePostUserRegister>() {
                        @Override
                        public void onResponse(Call<ResponsePostUserRegister> call, Response<ResponsePostUserRegister> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SignUpView.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpView.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePostUserRegister> call, Throwable t) {
                            Toast.makeText(SignUpView.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(SignUpView.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}