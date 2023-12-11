package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Models.PostUserLogin;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserLogin;
import com.example.interfaces_integradora.ViewModel.ViewModelLogin;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LogInView extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor myEdit;

    TextView btnforget;
    TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_view);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();


        EditText email = findViewById(R.id.editTextNombre);
        EditText password = findViewById(R.id.editTextContrasenia);
        Button btnlogin = findViewById(R.id.btnlogin);
        TextView btnsignup = findViewById(R.id.registrate);
        btnforget = findViewById(R.id.olvidasteContrasenia);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        if (revisarSesion()) {
            Intent intent = new Intent(LogInView.this, MyPlants.class);
            startActivity(intent);
            finish();
        } else {
            String mensaje = "No hay sesion iniciada";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        ViewModelLogin viewModel = new ViewModelProvider(this).get(ViewModelLogin.class);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                viewModel.loginUser(new PostUserLogin(userEmail, userPassword));
            }
        });

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                tvErrorMessage.setText(errorMessage);
            }
        });

        viewModel.getLoginresult().observe(this, new Observer<ResponsePostUserLogin>() {
            @Override
            public void onChanged(ResponsePostUserLogin response) {
                if (response != null) {
                    // Guardar el token en SharedPreferences
                    String fullToken = response.getToken_type() + " " + response.getAccess_token();
                    myEdit.putString("token", fullToken);
                    myEdit.putBoolean("sesion", true);
                    myEdit.apply();

                    // Navegar a la siguiente actividad
                    Intent intent = new Intent(LogInView.this, MyPlants.class);
                    startActivity(intent);
                } else {
                    // Mostrar un mensaje de error
                    Toast.makeText(LogInView.this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(LogInView.this, "Error de conexión: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String toastMessage) {
                Toast.makeText(LogInView.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInView.this, SignUpView.class);
                startActivity(intent);
            }
        });

        btnforget.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(LogInView.this, R.style.BottomSheetStyle);
            View sheetView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.bottom_dialog_forget,
                            (ViewGroup) findViewById(R.id.containerforget));

            Button enviar = sheetView.findViewById(R.id.enviarCorreo);
            EditText correo = sheetView.findViewById(R.id.correo);

            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = correo.getText().toString();

                    viewModel.forgetPassword(email);


                    bottomSheetDialog.dismiss();

                }
            });


            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });
    }

    public boolean revisarSesion() {
        return this.sharedPreferences.getBoolean("sesion", false);
    }
}