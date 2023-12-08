package com.example.interfaces_integradora;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

public class PlantsHolder extends RecyclerView.ViewHolder
{
    ShapeableImageView imageview;
    TextView nombre;

    public PlantsHolder(@NonNull View itemView)
    {
        super(itemView);
        imageview = itemView.findViewById(R.id.imagePlantView);
        nombre = itemView.findViewById(R.id.plantName);
    }
}
