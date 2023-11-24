package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MyPlants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

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