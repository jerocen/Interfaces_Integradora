package com.example.interfaces_integradora.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaces_integradora.DetallePlanta;
import com.example.interfaces_integradora.Models.ItemPlant;
import com.example.interfaces_integradora.R;
import com.example.interfaces_integradora.Retrofit.ResponseGetUserPlant;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PlantAdaptor extends RecyclerView.Adapter<PlantAdaptor.ViewHolder>{

    List<ResponseGetUserPlant.data> items;
    int[] images = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4};

    public PlantAdaptor(List<ResponseGetUserPlant.data> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public PlantAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_view, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallePlanta.class);
                v.getContext().startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdaptor.ViewHolder holder, int position) {
        ResponseGetUserPlant.data plant = items.get(position);
        holder.setData(plant);

        int randomImageIndex = (int) (Math.random() * images.length);
        holder.imageview.setImageResource(images[randomImageIndex]);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ShapeableImageView imageview;
        ResponseGetUserPlant.data pt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.plantName);
            imageview = itemView.findViewById(R.id.imagePlantView);
        }

        public void setData(ResponseGetUserPlant.data plant) {
            pt = plant;
            nombre.setText(plant.getName());
        }
    }
}
