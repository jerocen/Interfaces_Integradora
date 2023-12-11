package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserChangePassword;

import retrofit2.Call;

public class EditProfileInfo extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String token;

    EditText newpassword;
    EditText newpasswordconfirm;
    Button btnEdit;

    Peticiones peticiones = new Peticiones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        newpassword = findViewById(R.id.newPassword);
        newpasswordconfirm = findViewById(R.id.Confirmpassword);
        btnEdit = findViewById(R.id.btnConfirmarCambio);

        btnEdit.setOnClickListener(v -> {
            String password = newpassword.getText().toString();
            String passwordConfirm = newpasswordconfirm.getText().toString();

            if (password.equals(passwordConfirm)) {
                Call<ResponsePostUserChangePassword> call = peticiones.changePassword(token, password, passwordConfirm);

                call.enqueue(new retrofit2.Callback<ResponsePostUserChangePassword>() {
                    @Override
                    public void onResponse(Call<ResponsePostUserChangePassword> call, retrofit2.Response<ResponsePostUserChangePassword> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditProfileInfo.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePostUserChangePassword> call, Throwable t) {
                        Toast.makeText(EditProfileInfo.this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}