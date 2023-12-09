package com.example.interfaces_integradora.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.PlantsAdaptador;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPlants extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    String token;

    FloatingActionButton fab;

    Peticiones Peticiones = new Peticiones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        View headerView = navigationView.getHeaderView(0);
        TextView nombrePerfil = headerView.findViewById(R.id.nombreperfil);
        TextView correo = headerView.findViewById(R.id.correo);

        // Haz una llamada a la función obtenerDatosUser
        Call<ResponsePostUserMe> call = Peticiones.obtenerDatosUser(token);
        call.enqueue(new Callback<ResponsePostUserMe>() {
            @Override
            public void onResponse(Call<ResponsePostUserMe> call, Response<ResponsePostUserMe> response) {
                if (response.isSuccessful()) {
                    // Actualiza los TextViews con los datos del usuario
                    nombrePerfil.setText(response.body().getName());
                    correo.setText(response.body().getEmail());
                } else {
                    Toast.makeText(MyPlants.this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePostUserMe> call, Throwable t) {
                Toast.makeText(MyPlants.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });


        fab.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MyPlants.this, R.style.BottomSheetStyle);
            View sheetView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.bottom_dialog,
                            (ViewGroup) findViewById(R.id.container));

            Button agregarPlantaButton = sheetView.findViewById(R.id.agregarPlanta);
            EditText nombrePlantaEditText = sheetView.findViewById(R.id.nombrePlanta);

            agregarPlantaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombrePlanta = nombrePlantaEditText.getText().toString();

                    Call<ResponsePostUserPlant> call = Peticiones.createplant(token, nombrePlanta);
                    call.enqueue(new Callback<ResponsePostUserPlant>() {
                        @Override
                        public void onResponse(Call<ResponsePostUserPlant> call, Response<ResponsePostUserPlant> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MyPlants.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyPlants.this, "Error al crear la planta", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePostUserPlant> call, Throwable t) {
                            Toast.makeText(MyPlants.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Cierra el BottomDialog
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });

        
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_my_plants);


        RecyclerView recyclerView = findViewById(R.id.recyclerviewPlants);

        List<ItemPlant> itemPlants = new ArrayList<ItemPlant>();
        itemPlants.add(new ItemPlant("Lirios", R.drawable.icon1));
        itemPlants.add(new ItemPlant("Tulipan", R.drawable.icon2));
        itemPlants.add(new ItemPlant("Margarita", R.drawable.icon3));
        itemPlants.add(new ItemPlant("Orquídeas", R.drawable.icon4));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new PlantsAdaptador(getApplicationContext(),itemPlants));

        PlantsAdaptador adapter = new PlantsAdaptador(getApplicationContext(), itemPlants);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
            Peticiones.logoutUser(this, token, () -> {
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