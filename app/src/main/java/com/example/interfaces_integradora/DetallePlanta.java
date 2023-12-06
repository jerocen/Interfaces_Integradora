package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;


public class DetallePlanta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_planta);
        /*
        Intent intent = getIntent();
        if (intent != null) {
            ItemPlant selectedPlant = (ItemPlant) intent.getSerializableExtra("PLANTA_SELECCIONADA");


            if (selectedPlant != null) {

                TextView textViewNombre = findViewById(R.id.textview_nombre_planta);
                textViewNombre.setText(selectedPlant.getNombre());

                ImageView imageViewPlanta = findViewById(R.id.imageview_planta);
                imageViewPlanta.setImageResource(selectedPlant.getImage());
            }
        }
        */

    }
}