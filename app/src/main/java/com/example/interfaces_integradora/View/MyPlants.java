package com.example.interfaces_integradora.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.Adapters.PlantAdaptor;
import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.Models.Peticiones;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserPlant;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserMe;
import com.example.interfaces_integradora.Retrofit.ResponsePostUserPlant;
import com.example.interfaces_integradora.Utility.NetworkChangeListener;
import com.example.interfaces_integradora.ViewModel.ViewModelMyPlant;
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

    ViewModelMyPlant viewModel;

    Peticiones Peticiones = new Peticiones();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

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
        TextView nombrePerfilTextView = headerView.findViewById(R.id.nombreperfil);
        TextView correoTextView = headerView.findViewById(R.id.correo);

        viewModel = new ViewModelProvider(this).get(ViewModelMyPlant.class);

// Observa los cambios en el nombre del perfil y el correo
        viewModel.getNombrePerfil().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String nombrePerfil) {
                nombrePerfilTextView.setText(nombrePerfil);
            }
        });

        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String toastMessage) {
                Toast.makeText(MyPlants.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCorreo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String correo) {
                correoTextView.setText(correo);
            }
        });
        viewModel.obtenerDatosUser(token);

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

                    viewModel.agregarPlanta(token, nombrePlanta);


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

        viewModel.getItemPlants().observe(this, new Observer<List<ResponseGetUserPlant.data>>() {
            @Override
            public void onChanged(List<ResponseGetUserPlant.data> groups) {
                // Actualiza el adaptador de tu RecyclerView
                PlantAdaptor adapter = new PlantAdaptor(groups);
                recyclerView.setLayoutManager(new GridLayoutManager(MyPlants.this, 2));
                recyclerView.setAdapter(adapter);
            }
        });

        // Obtén los datos iniciales
        viewModel.obtenerDatosPlant(token);
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


    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}