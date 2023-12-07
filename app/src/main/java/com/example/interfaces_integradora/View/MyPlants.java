package com.example.interfaces_integradora.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.Models.AuthLogout;
import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.PlantsAdaptador;
import com.example.interfaces_integradora.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MyPlants extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    Button buttonprueba;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        buttonprueba = findViewById(R.id.buttonprueba);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_my_plants);


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

        PlantsAdaptador adapter = new PlantsAdaptador(getApplicationContext(), itemPlants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer((GravityCompat.START));
        }else{
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
            Log.d("MyPlants", "Token: " + token);
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            AuthLogout.logoutUser(this, token, () -> {
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