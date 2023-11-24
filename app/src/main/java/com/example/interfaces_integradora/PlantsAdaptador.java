package com.example.interfaces_integradora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantsAdaptador extends RecyclerView.Adapter<PlantsHolder>
{

    Context context;
    List<ItemPlant> items;

    public PlantsAdaptador(Context context, List<ItemPlant> items)
    {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PlantsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantsHolder(LayoutInflater.from(context).inflate(R.layout.item_plant_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsHolder holder, int position)
    {
        holder.nombre.setText(items.get(position).getNombre());
        holder.usuario.setText(items.get(position).getUsuario());
        holder.imageview.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
