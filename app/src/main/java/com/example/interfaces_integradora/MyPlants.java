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
        itemPlants.add(new ItemPlant("Jessica", "saml@gmail.com", R.drawable.ImagenPlanta1));
        itemPlants.add(new ItemPlant("Phoebe", "johnvald@gmail.com", R.drawable.ImagenPlanta2));
        itemPlants.add(new ItemPlant("Raul", "gabymend@hotmail.com", R.drawable.ImagenPlanta3));
        itemPlants.add(new ItemPlant("Erick", "mollychav@gmail.com", R.drawable.ImagenPlanta4));
        itemPlants.add(new ItemPlant("Samantha", "sarahbaraj@gmail.com", R.drawable.ImagenPlanta5));
        itemPlants.add(new ItemPlant("Agatha", "ismamart@gmail.com", R.drawable.ImagenPlanta6));
        itemPlants.add(new ItemPlant("Nicole", "sofiroj@gmail.com", R.drawable.ImagenPlanta7));
        itemPlants.add(new ItemPlant("Luan", "martdiz@gmail.com", R.drawable.ImagenPLanta8));
        itemPlants.add(new ItemPlant("Karl", "josuern@gmail.com", R.drawable.ImagenPlanta9));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlantsAdaptador(getApplicationContext(),itemPlants));
    }
}