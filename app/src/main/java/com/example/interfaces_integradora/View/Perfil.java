package com.example.interfaces_integradora.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;
import com.example.interfaces_integradora.ViewModel.ViewModelMyPlant;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;

public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sharedPreferences;
    TextView nombre, correo;
    String token;
    Button btnEdit;
    Peticiones peticiones = new Peticiones();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ViewModelMyPlant viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);



        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        TextView nombrePerfilTextView = headerView.findViewById(R.id.nombreperfil);
        TextView correoTextView = headerView.findViewById(R.id.correo);

        viewModel = new ViewModelProvider(this).get(ViewModelMyPlant.class);

        viewModel.getNombrePerfil().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String nombrePerfil) {
                nombrePerfilTextView.setText(nombrePerfil);
            }
        });

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String toastMessage) {
                Toast.makeText(Perfil.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCorreo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String correo) {
                correoTextView.setText(correo);
            }
        });
        viewModel.obtenerDatosUser(token);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_perfil);
        getSupportActionBar().setTitle("Mi perfil");

        nombre = findViewById(R.id.nom);
        correo = findViewById(R.id.ema);
        btnEdit = findViewById(R.id.btn);

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(Perfil.this, EditProfileInfo.class);
            startActivity(i);
        });

        Call<ResponsePostUserMe> call = peticiones.obtenerDatosUser(token);
        call.enqueue(new retrofit2.Callback<ResponsePostUserMe>() {
            @Override
            public void onResponse(Call<ResponsePostUserMe> call, retrofit2.Response<ResponsePostUserMe> response) {
                if (response.isSuccessful()) {
                    nombre.setText(response.body().getName());
                    correo.setText(response.body().getEmail());

                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserMe> call, Throwable t) {
               Intent i = new Intent(Perfil.this, LogInView.class);
               startActivity(i);
               finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_my_plants) {
            Intent intent = new Intent(this, MyPlants.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_contact) {
            Intent intent = new Intent(this, Contact.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_logout) {
            Log.d("Mi cuenta", "Token: " + token);
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            peticiones.logoutUser(this, token, () -> {
                Intent intent = new Intent(this, LogInView.class);
                startActivity(intent);
                finish();
            });
        } else if (itemId == R.id.nav_about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}