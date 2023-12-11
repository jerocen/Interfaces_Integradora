package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Models.PostUserRegister;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserRegister;
import com.example.interfaces_integradora.ViewModel.ViewModelSingUp;

import java.util.List;
import java.util.Map;

public class SignUpView extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);

        EditText nombre = findViewById(R.id.editTextNombreRegistro);
        EditText email = findViewById(R.id.editTextEmailRegistro);
        EditText password = findViewById(R.id.editTextContraseniaRegistro);
        EditText passwordConfirm = findViewById(R.id.editTextConfirmarContraseniaRegistro);
        Button btnRegistro = findViewById(R.id.btnRegistro);
        TextView errorNombre = findViewById(R.id.errorNombre);
        TextView errorEmail = findViewById(R.id.errorEmail);
        TextView errorPassword = findViewById(R.id.errorPassword);

        ViewModelSingUp viewModel = new ViewModelProvider(this).get(ViewModelSingUp.class);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorNombre.setText("");
                errorEmail.setText("");
                errorPassword.setText("");

                String name = nombre.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userPasswordConfirm = passwordConfirm.getText().toString();

                if (userPassword.equals(userPasswordConfirm)) {
                    viewModel.registerUser(new PostUserRegister(name, userEmail, userPassword, userPasswordConfirm));
                } else {
                    Toast.makeText(SignUpView.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getRegisterResult().observe(this, new Observer<ResponsePostUserRegister>() {
            @Override
            public void onChanged(ResponsePostUserRegister response) {
                if (response != null) {
                    Toast.makeText(SignUpView.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpView.this, "Registro denegado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getErrorResponse().observe(this, new Observer<ResponsePostUserRegister.ErrorResponse>() {
            @Override
            public void onChanged(ResponsePostUserRegister.ErrorResponse errorResponse) {
                if (errorResponse != null) {
                    Map<String, List<String>> errors = errorResponse.getData();
                    if (errors.containsKey("name")) {
                        errorNombre.setText(errors.get("name").get(0));
                    }
                    if (errors.containsKey("email")) {
                        errorEmail.setText(errors.get("email").get(0));
                    }
                    if (errors.containsKey("password")) {
                        errorPassword.setText(errors.get("password").get(0));
                    }
                }
            }
        });
    }
}