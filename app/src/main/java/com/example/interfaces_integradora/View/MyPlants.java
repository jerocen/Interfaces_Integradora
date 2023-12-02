package com.example.interfaces_integradora.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.interfaces_integradora.Models.AuthLogout;
import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.PlantsAdaptador;
import com.example.interfaces_integradora.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MyPlants extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    Button buttonprueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        buttonprueba = findViewById(R.id.buttonprueba);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        toolbar.setNavigationOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


        buttonprueba.setOnClickListener(v -> {
            String token = sharedPreferences.getString("token", "");
            Log.d("MyPlants", "Token: " + token);
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            AuthLogout.logoutUser(this, token);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerviewPlants);

        List<ItemPlant> itemPlants = new ArrayList<ItemPlant>();
        itemPlants.add(new ItemPlant("Lirios", R.drawable.imagen_planta1));
        itemPlants.add(new ItemPlant("Tulipan", R.drawable.imagen_planta2));
        itemPlants.add(new ItemPlant("Margarita", R.drawable.imagen_planta3));
        itemPlants.add(new ItemPlant("Orquídeas", R.drawable.imagen_planta4));
        itemPlants.add(new ItemPlant("Rosa", R.drawable.imagen_planta5));
        itemPlants.add(new ItemPlant("Girasol", R.drawable.imagen_planta6));
        itemPlants.add(new ItemPlant("Peonías", R.drawable.imagen_planta7));
        itemPlants.add(new ItemPlant("Narcisos", R.drawable.imagen_planta8));
        itemPlants.add(new ItemPlant("Dalias", R.drawable.imagen_planta9));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlantsAdaptador(getApplicationContext(),itemPlants));
    }
}