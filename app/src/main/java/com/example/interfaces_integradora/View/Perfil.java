package com.example.interfaces_integradora.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserMe;
import com.example.interfaces_integradora.ViewModel.ViewModelPerfil;
import com.google.android.material.navigation.NavigationView;

public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sharedPreferences;
    TextView nombre, correo,cant;
    String token;
    Button btnEdit;
    Peticiones peticiones = new Peticiones();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ViewModelPerfil perfilViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        cant = findViewById(R.id.cant);
        View headerView = navigationView.getHeaderView(0);
        TextView nombrePerfilTextView = headerView.findViewById(R.id.nombreperfil);
        TextView correoTextView = headerView.findViewById(R.id.correo);

        perfilViewModel = new ViewModelProvider(this).get(ViewModelPerfil.class);
        perfilViewModel.getUserData().observe(this, new Observer<ResponseGetUserMe>() {
            @Override
            public void onChanged(ResponseGetUserMe responseGetUserMe) {
                nombre.setText(responseGetUserMe.getName());
                correo.setText(responseGetUserMe.getEmail());
                cant.setText(responseGetUserMe.getPlants());

                nombrePerfilTextView.setText(responseGetUserMe.getName());
                correoTextView.setText(responseGetUserMe.getEmail());
            }
        });

        perfilViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Manejar el error aquí
            }
        });

        perfilViewModel.obtenerDatosUser(token);

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