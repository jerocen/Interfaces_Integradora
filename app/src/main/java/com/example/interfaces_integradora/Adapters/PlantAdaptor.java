package com.example.interfaces_integradora.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PlantAdaptor extends RecyclerView.Adapter<PlantAdaptor.ViewHolder>{

    List<ItemPlant> items;

    public PlantAdaptor(List<ItemPlant> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public PlantAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdaptor.ViewHolder holder, int position) {
        ItemPlant plant = items.get(position);
        holder.setData(plant);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ShapeableImageView imageview;
        ItemPlant pt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.plantName);
            imageview = itemView.findViewById(R.id.imagePlantView);
        }

        public void setData(ItemPlant plant) {
            pt = plant;
            nombre.setText(plant.getNombre());
            imageview.setImageResource(plant.getImage());
        }
    }
}
